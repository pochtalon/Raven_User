package com.example.raven.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerCreateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String fullName;
    @NotBlank
    @Size(min = 2, max = 100)
    @Email
    private String email;
    @Size(min = 6, max = 14)
    @Pattern(regexp = "\\+[0-9]{5,13}")
    private String phone;
}
