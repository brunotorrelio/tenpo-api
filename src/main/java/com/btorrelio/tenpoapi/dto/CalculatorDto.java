package com.btorrelio.tenpoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculatorDto {

    private BigDecimal number1;

    private BigDecimal number2;

    private BigDecimal result;

}
