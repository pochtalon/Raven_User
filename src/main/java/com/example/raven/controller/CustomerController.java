package com.example.raven.controller;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.example.raven.dto.CustomerUpdateDto;
import com.example.raven.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public CustomerDto addCustomer(@RequestBody @Valid CustomerCreateDto createDto) {
        return customerService.saveCustomer(createDto);
    }

    @GetMapping
    @Operation(summary = "Get all customers",
            description = "Get list of all customers")
    public List<CustomerDto> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id", description = "Get customer by id")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer by id", description = "Update customer by id")
    public CustomerDto updateProject(@PathVariable Long id,
                                    @Valid @RequestBody CustomerUpdateDto updateDto) {
        return customerService.updateCustomer(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer by id", description = "Delete customer by id")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
