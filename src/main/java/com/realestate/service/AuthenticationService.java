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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        //!!! request icinden email ve password aliniyor
        String email =loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // !!! AuthenticationManager uzerinden kullaniciyi valide ediyoruz
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        // !!! Valide edilen user context e gonderiliyor
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // !!! JWT token olusturuluyor
        String token = "Bearer " + jwtUtils.generateJwtToken(authentication); // Bearer jhkjsdhfi76w4wghfdsuigfd7wgh

        // !!! response icindeki fieldlar dolduruluyor
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles  = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst();

        // !!! Response nesnesi olusturuluyor
        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.token(token.substring(7));


        return ResponseEntity.ok(authResponse.build());

    }
}
