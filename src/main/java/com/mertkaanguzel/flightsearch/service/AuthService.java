package com.mertkaanguzel.flightsearch.service;

import com.mertkaanguzel.flightsearch.dto.CreateUserDto;
import com.mertkaanguzel.flightsearch.dto.UserDto;
import com.mertkaanguzel.flightsearch.exception.ResourceAlreadyExistsException;
import com.mertkaanguzel.flightsearch.exception.ResourceNotFoundException;
import com.mertkaanguzel.flightsearch.model.Role;
import com.mertkaanguzel.flightsearch.model.UserAccount;
import com.mertkaanguzel.flightsearch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final Clock clock;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, Clock clock) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.clock = clock;
    }


    public UserAccount createUser(CreateUserDto userDto) {
        if (userRepository.findByUsername(userDto.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists");
        }

        UserAccount user = UserAccount.builder()
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password()))
                .role(Role.ROLE_USER)
                .build();

        return userRepository.saveAndFlush(user);
    }

    public UserAccount findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDto getUserByName(String username, Authentication authentication) {
        UserAccount user = findUserByName(username);
        return new UserDto(user.username(), generateToken(authentication));
    }
/*
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        return token.getToken().getTokenValue();
    }
*/
    public String generateToken(Authentication authentication) {
        Instant now = getInstant();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .claim("scope", scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private Instant getInstant() {
        return clock.instant();
    }


}
