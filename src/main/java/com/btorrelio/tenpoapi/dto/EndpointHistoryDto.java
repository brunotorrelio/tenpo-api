package com.btorrelio.tenpoapi.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHistoryDto {

    private Long id;

    private String endpointType;

    private String endpointUrl;

    private JsonNode jsonResponse;

    private LocalDateTime createdAt;

}
