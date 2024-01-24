package com.example.raven.service;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.example.raven.dto.CustomerUpdateDto;
import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerCreateDto requestDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getById(Long id);

    CustomerDto updateCustomer(Long id, CustomerUpdateDto updateDto);

    void deleteCustomer(Long id);
}
