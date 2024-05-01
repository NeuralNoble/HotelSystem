package com.group18.HotelSystem.services.auth;

import com.group18.HotelSystem.dto.SignupRequest;
import com.group18.HotelSystem.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
