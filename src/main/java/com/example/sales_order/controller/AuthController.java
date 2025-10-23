package com.example.sales_order.controller;

import com.example.sales_order.security.CustomUserDetails;
import com.example.sales_order.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody @Valid AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String role = ((CustomUserDetails) auth.getPrincipal()).getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String token = jwtUtils.generateToken(request.getUsername(), role);

        return new JwtResponse(token);
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
}

@Data
class JwtResponse {
    private final String token;
}
