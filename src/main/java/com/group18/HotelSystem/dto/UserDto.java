package com.group18.HotelSystem.dto;

import com.group18.HotelSystem.enums.UserRole;
import lombok.Data;


@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
