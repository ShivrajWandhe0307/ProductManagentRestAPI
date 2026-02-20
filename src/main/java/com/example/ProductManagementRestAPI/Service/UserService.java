package com.example.ProductManagementRestAPI.Service;


import com.example.ProductManagementRestAPI.Configuration.AuthResponse;
import com.example.ProductManagementRestAPI.DTO.UserDTO;

public interface UserService
{
    public UserDTO newUser(UserDTO userDTO);

    public AuthResponse loginUser(String userName, String password);
}
