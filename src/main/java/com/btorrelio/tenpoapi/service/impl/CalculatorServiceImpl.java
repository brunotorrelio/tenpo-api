package com.btorrelio.tenpoapi.service.impl;

import com.btorrelio.tenpoapi.dto.CalculatorDto;
import com.btorrelio.tenpoapi.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public CalculatorDto add(BigDecimal number1, BigDecimal number2) {
        log.info("Calculanding addition between " + number1 + " and " + number2);

        number1 = replaceNullForZero(number1);
        number2 = replaceNullForZero(number2);

       return CalculatorDto.builder()
               .number1(number1)
               .number2(number2)
               .result(number1.add(number2))
               .build();
    }

    private BigDecimal replaceNullForZero(BigDecimal number) {
        return number == null ? BigDecimal.ZERO : number;
    }
}
