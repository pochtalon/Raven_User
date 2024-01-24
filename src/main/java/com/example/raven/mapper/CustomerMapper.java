package com.example.raven.mapper;

import com.example.raven.config.MapperConfig;
import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.example.raven.dto.CustomerUpdateDto;
import com.example.raven.model.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CustomerMapper {
    Customer toModel(CustomerCreateDto createDto);

    Customer toModel(CustomerUpdateDto updateDto);

    CustomerDto toDto(Customer customer);
}
