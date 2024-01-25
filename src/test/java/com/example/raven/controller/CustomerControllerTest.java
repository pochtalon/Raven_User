package com.example.raven.controller;

import com.example.raven.dto.CustomerCreateDto;
import com.example.raven.dto.CustomerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final Long ID = 1994L;
    private static final String EMAIL = "eric.draven@mail.com";
    private static final String FULL_NAME = "Eric Draven";
    private static final String PHONE = "+380969609696";

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        clearTable(dataSource);
    }

    @BeforeEach
    @SneakyThrows
    void addEntitiesToDataBase(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/customers/add-two-customers-to-db.sql")
            );
        }
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        clearTable(dataSource);
    }

    @SneakyThrows
    private static void clearTable(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/customers/clear-customers-table.sql")
            );
        }
    }

    @Test
    @DisplayName("Create new customer")
    public void addCustomer_CustomerCreateDto_ReturnCustomerDto() throws Exception {
        CustomerCreateDto createDto = new CustomerCreateDto()
                .setEmail(EMAIL)
                .setPhone(PHONE)
                .setFullName(FULL_NAME);
        String jsonRequest = objectMapper.writeValueAsString(createDto);

        MvcResult result = mockMvc.perform(post("/api/customers")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDto expected = new CustomerDto()
                .setPhone(PHONE)
                .setEmail(EMAIL)
                .setFullName(FULL_NAME);
        CustomerDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerDto.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}