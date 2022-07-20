package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.CalculatorDto;
import com.btorrelio.tenpoapi.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CalculatorControllerTest extends AbstractControllerTest {

    private final String BASE_URL_API = "/calculator";

    @MockBean
    private CalculatorService calculatorServiceMock;

    @Test
    @WithMockUser
    void given_number1AndNumber2_when_AdditionIsCallen_then_returnCalculationResult() throws Exception{

        CalculatorDto dto = CalculatorDto.builder()
                        .number1(new BigDecimal("10.20"))
                        .number2(new BigDecimal("5.50"))
                        .result(new BigDecimal("15.70"))
                        .build();

        when(calculatorServiceMock.add(any(), any())).thenReturn(dto);

        mockMvc.perform(get(BASE_URL_API + "/addition")
                        .queryParam("number1", "10.20")
                        .queryParam("number2", "5.50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number1").value(10.20))
                .andExpect(jsonPath("$.number2").value(5.50))
                .andExpect(jsonPath("$.result").value(15.70));

    }

    @Test
    @WithMockUser
    void given_number1ButNotNumber2_When_AdditionIsCallen_Then_returnBadRequest() throws Exception{

        mockMvc.perform(get(BASE_URL_API + "/addition")
                        .queryParam("number1", "10.20"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @WithMockUser
    void given_number2ButNotNumber1_When_AdditionIsCallen_Then_returnBadRequest() throws Exception{

        mockMvc.perform(get(BASE_URL_API + "/addition")
                        .queryParam("number2", "10.20"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @WithMockUser
    void given_number1AndNumber2ButIsString_When_AdditionIsCallen_Then_returnBadRequest() throws Exception{

        mockMvc.perform(get(BASE_URL_API + "/addition")
                        .queryParam("number1", "hola")
                        .queryParam("number2", "10.20"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void given_number1AndNumber2WithoutAuthentication_When_AdditionIsCallen_Then_returnUnauthorized() throws Exception{

        mockMvc.perform(get(BASE_URL_API + "/addition")
                        .queryParam("number1", "11.50")
                        .queryParam("number2", "10.20"))
                .andExpect(status().isUnauthorized());

    }

}
