package com.mertkaanguzel.flightsearch.controller;

import com.mertkaanguzel.flightsearch.dto.CreateUserDto;
import com.mertkaanguzel.flightsearch.dto.LoginDto;
import com.mertkaanguzel.flightsearch.dto.UserDto;
import com.mertkaanguzel.flightsearch.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody CreateUserDto userDto) {
        authService.createUser(userDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password())
        );
        return ResponseEntity.ok(authService.getUserByName(userDto.username(), authentication));
    }
}
