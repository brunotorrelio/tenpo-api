package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.CalculatorDto;

import java.math.BigDecimal;

public interface CalculatorService {

    CalculatorDto add(BigDecimal number1, BigDecimal number2);

}
