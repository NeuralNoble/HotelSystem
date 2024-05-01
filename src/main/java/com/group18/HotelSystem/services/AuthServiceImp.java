package com.group18.HotelSystem.services;

import com.group18.HotelSystem.entity.User;
import com.group18.HotelSystem.enums.UserRole;
import com.group18.HotelSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp {
    private final UserRepository userRepository;

    public void createAnAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin  created successfully");
        } else {
            System.out.println("Admin  already exists");
        }
    }
}