package com.example.raven.controller;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.example.raven.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Customer management", description = "Endpoints for managing customers")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create new customer", description = "Create new customer")
    public CustomerDto addProject(@RequestBody @Valid CustomerCreateDto requestDto) {
        return customerService.save(requestDto);
    }


}
