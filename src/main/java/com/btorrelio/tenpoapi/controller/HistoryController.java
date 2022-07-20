package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.dto.PaginationDto;
import com.btorrelio.tenpoapi.service.EndpointHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Tag(name = "Endpoints History")
@RestController
@RequestMapping(value = "history", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoryController {

    private final EndpointHistoryService endpointHistoryService;

    public HistoryController(EndpointHistoryService endpointHistoryService) {
        this.endpointHistoryService = endpointHistoryService;
    }

    @GetMapping
    public ResponseEntity<PaginationDto<EndpointHistoryDto>> getAll(
            @RequestParam @NotNull Integer page,
            @RequestParam @NotNull Integer rows
    ) {
        return ResponseEntity.ok(endpointHistoryService.getAll(page, rows));
    }

}
