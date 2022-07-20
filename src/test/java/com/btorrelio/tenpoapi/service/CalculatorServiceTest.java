package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.CalculatorDto;
import com.btorrelio.tenpoapi.service.impl.CalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorServiceImpl.class)
class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    void given_number1AndNumber2_when_addIsCalled_thenResult() {
        // Given
        BigDecimal number1 = new BigDecimal("10.50");
        BigDecimal number2 = new BigDecimal("5.75");

        // When
        CalculatorDto calculatorDto = calculatorService.add(number1, number2);

        // Then
        assertNotNull(calculatorDto);
        assertEquals(number1, calculatorDto.getNumber1());
        assertEquals(number2, calculatorDto.getNumber2());
        assertEquals(new BigDecimal("16.25"), calculatorDto.getResult());

    }

    @Test
    void given_number1NullAndNumber2Null_when_addIsCalled_thenResultZero() {
        // Given
        BigDecimal number1 = null;
        BigDecimal number2 = null;

        // When
        CalculatorDto calculatorDto = calculatorService.add(number1, number2);

        // Then
        assertNotNull(calculatorDto);
        assertEquals(BigDecimal.ZERO, calculatorDto.getNumber1());
        assertEquals(BigDecimal.ZERO, calculatorDto.getNumber2());
        assertEquals(BigDecimal.ZERO, calculatorDto.getResult());

    }

}
