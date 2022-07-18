package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.CalculatorDto;
import com.btorrelio.tenpoapi.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Tag(name = "Calculator")
@RestController
@RequestMapping(value = "calculator", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("addition")
    @Operation(summary = "Add two numbers", description = "service to add two numbers", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<CalculatorDto> add(@RequestParam @NotNull(message = "Number1 must not be null") BigDecimal number1,
                                             @RequestParam @NotNull(message = "Number2 must not be null") BigDecimal number2) {
        return ResponseEntity.ok(calculatorService.add(number1, number2));
    }

}
