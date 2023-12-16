package com.realestate.service;

import com.realestate.payload.request.LoginRequest;
import com.realestate.payload.response.AuthResponse;
import com.realestate.security.jwt.JwtUtils;
import com.realestate.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


  

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;


    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {


        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()

                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());


        Optional<String> role = roles.stream().findFirst();

        System.out.println("role : " + role);


        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();

        authResponse.token(token.substring(7));
        if(role.isPresent()){
            authResponse.role(role.get());
        }

        return ResponseEntity.ok(authResponse.build());







    }


}

