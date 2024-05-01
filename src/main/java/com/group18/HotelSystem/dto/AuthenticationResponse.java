package com.group18.HotelSystem.dto;


import com.group18.HotelSystem.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private  String jwt;
    private Long userId;
    private UserRole userRole;
}
