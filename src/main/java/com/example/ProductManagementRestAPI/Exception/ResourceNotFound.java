package com.example.ProductManagementRestAPI.Exception;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String message)
    {
        super(message);
    }
}
