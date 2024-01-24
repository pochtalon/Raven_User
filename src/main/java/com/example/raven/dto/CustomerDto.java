package com.example.raven.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
}
