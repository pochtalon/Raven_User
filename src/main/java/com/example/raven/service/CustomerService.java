package com.example.raven.service;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;

public interface CustomerService {
    CustomerDto save(CustomerCreateDto requestDto);
}
