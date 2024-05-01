package com.group18.HotelSystem.controller.auth;


import com.group18.HotelSystem.dto.AuthenticationRequest;
import com.group18.HotelSystem.dto.AuthenticationResponse;
import com.group18.HotelSystem.dto.SignupRequest;
import com.group18.HotelSystem.dto.UserDto;
import com.group18.HotelSystem.entity.User;
import com.group18.HotelSystem.repository.UserRepository;
import com.group18.HotelSystem.services.auth.AuthService;
import com.group18.HotelSystem.utill.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try{
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser , HttpStatus.OK);
        }catch(EntityExistsException entityExistsException){
            return new ResponseEntity<>("User alraedy exists",HttpStatus.NOT_ACCEPTABLE);

        }catch(Exception e){
            return new ResponseEntity<>("User not created, come again later",HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = null;
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = JwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if((optionalUser.isPresent())){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return  authenticationResponse;

    }
}
