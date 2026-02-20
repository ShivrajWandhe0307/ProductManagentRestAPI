package com.example.ProductManagementRestAPI.Exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {

    private int statusCode;
    private String status;
    private String path;
    private String message;
    private LocalTime time;
}
