package com.example.raven.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerUpdateDto {
    @Size(min = 2, max = 50)
    private String fullName;
    @Size(min = 6, max = 14)
    @Pattern(regexp = "\\+[0-9]{5,13}")
    private String phone;
}
