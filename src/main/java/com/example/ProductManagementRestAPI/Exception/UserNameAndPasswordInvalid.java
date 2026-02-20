package com.example.ProductManagementRestAPI.Exception;

public class UserNameAndPasswordInvalid extends RuntimeException{
    public UserNameAndPasswordInvalid(String message)
    {
        super(message);
    }
}
