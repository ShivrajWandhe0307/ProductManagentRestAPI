package com.example.ProductManagementRestAPI.Exception;

import com.example.ProductManagementRestAPI.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message)
    {
        super(message);
    }
}
