package com.realestate.service;

import com.realestate.entity.User;
import com.realestate.payload.request.LoginRequest;
import com.realestate.payload.request.LoginRequestWithGoogle;
import com.realestate.payload.response.AuthResponse;
import com.realestate.repository.UserRepository;
import com.realestate.security.jwt.JwtUtils;
import com.realestate.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {


        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        Set<String> role = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        System.out.println("role : " + role);

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();

        authResponse.token(token.substring(7));

        authResponse.role(String.join(" ", role));

        return ResponseEntity.ok(authResponse.build());

    }


    public ResponseEntity<AuthResponse> authenticateUserWithGoogle(LoginRequestWithGoogle loginRequestWithGoogle) {

        User user = userRepository.findByEmailEquals(loginRequestWithGoogle.getEmail());

        String encodedPassword = "123456Aa*";

        passwordEncoder.matches(user.getPasswordHash(), passwordEncoder.encode(encodedPassword));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), encodedPassword));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        Set<String> role = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.token(jwtToken);
        authResponse.role(String.join(" ", role));

        return ResponseEntity.ok(authResponse.build());
    }


}

