package com.example.raven.service;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.example.raven.dto.CustomerUpdateDto;
import com.example.raven.exception.EntityNotFoundException;
import com.example.raven.mapper.CustomerMapper;
import com.example.raven.model.Customer;
import com.example.raven.repository.CustomerRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerServiceImpl customerService;
    private static final Long ID = 1994L;
    private static final String EMAIL = "eric.draven@mail.com";
    private static final String FULL_NAME = "Eric Draven";
    private static final String PHONE = "+380969609696";

    @Test
    @DisplayName("Adding new customer")
    public void saveCustomer_ValidCustomerCreateDto_ReturnCustomerDto(){
        CustomerCreateDto requestDto = new CustomerCreateDto()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);
        Customer customer = new Customer()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);
        CustomerDto expected = new CustomerDto()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);

        when(customerMapper.toModel(requestDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(expected);

        CustomerDto actual = customerService.saveCustomer(requestDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all customers")
    public void getAllCustomers_ReturnListOfCustomers(){
        Customer customer = new Customer()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);
        CustomerDto customerDto = new CustomerDto()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);
        List<Customer> customers = List.of(customer);
        List<CustomerDto> expected = List.of(customerDto);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.toDto(customer)).thenReturn(customerDto);

        List<CustomerDto> actual = customerService.getAllCustomers();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get customer by valid id")
    public void getById_ValidId_ReturnCustomerDto(){
        Customer customer = new Customer()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);
        CustomerDto expected = new CustomerDto()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(expected);

        CustomerDto actual = customerService.getById(ID);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get customer by invalid id")
    public void getById_InvalidId_ThrowException(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> customerService.getById(ID));
        String expected = "Can't find customer with id " + ID;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update customer by valid id")
    public void updateCustomer_ValidIdAndDto_ReturnCustomerDto(){
        CustomerUpdateDto updateDto = new CustomerUpdateDto()
                .setFullName(FULL_NAME)
                .setPhone(PHONE);
        Customer customer = new Customer()
                .setEmail(EMAIL)
                .setFullName("Allan Poe")
                .setPhone("+380502641937");
        CustomerDto expected = new CustomerDto()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setPhone(PHONE);

        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(expected);

        CustomerDto actual = customerService.updateCustomer(ID, updateDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update customer by invalid id")
    public void updateCustomer_InvalidId_ThrowException(){
        CustomerUpdateDto updateDto = new CustomerUpdateDto();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> customerService.updateCustomer(ID, updateDto));
        String expected = "Can't find customer with id " + ID;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}