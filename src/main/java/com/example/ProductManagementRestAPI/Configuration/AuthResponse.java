package com.example.ProductManagementRestAPI.Configuration;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {

    private String fullName;
    private String accessToken;
    private String refreshToken;
}
