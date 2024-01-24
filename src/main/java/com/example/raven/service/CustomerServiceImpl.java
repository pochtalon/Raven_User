package com.example.raven.service;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.example.raven.dto.CustomerUpdateDto;
import com.example.raven.exception.EntityNotFoundException;
import com.example.raven.mapper.CustomerMapper;
import com.example.raven.model.Customer;
import com.example.raven.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto save(CustomerCreateDto requestDto) {
        Customer customer = customerMapper.toModel(requestDto);
        LocalDateTime creatingTime = LocalDateTime.now();
        customer.setCreated(creatingTime);
        customer.setUpdated(creatingTime);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        return customerMapper.toDto(customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find customer with id " + id)));
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerUpdateDto updateDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find customer with id " + id));
        correctCustomer(customer, updateDto);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private void correctCustomer(Customer customer, CustomerUpdateDto updateDto) {
        if (updateDto.getFullName() != null) {
            customer.setFullName(updateDto.getFullName());
        }
        if (updateDto.getPhone() != null) {
            customer.setPhone(updateDto.getPhone());
        }
        customer.setUpdated(LocalDateTime.now());
    }
}
