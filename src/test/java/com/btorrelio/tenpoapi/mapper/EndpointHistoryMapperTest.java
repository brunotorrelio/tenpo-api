package com.btorrelio.tenpoapi.mapper;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.entity.EndpointHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EndpointHistoryMapperImpl.class)
class EndpointHistoryMapperTest {

    @Autowired
    private EndpointHistoryMapper endpointHistoryMapper;

    @Test
    void given_endpointHistoryEntity_when_entity2DtoIsCalled_then_returnDto() {
        // Given
        String uri = "/api/users/login";
        String method = "POST";
        String jsonResponse = "{\n  \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE2NTgyMTcwNzUsImlhdCI6MTY1ODE5OTA3NX0.HYcUC8mO5ahVENOM8biono2SQLQDyO-ZiEPZ_DPFc7Hgtd0kiECsA-KtZtR6mpZjHbunwbY72QYma795jvcuEw\",\n  \"timeStamp\": \"2022-07-18T23:51:15.52\"\n}";

        EndpointHistory endpointHistoryEntity = EndpointHistory.builder()
                .id(1L)
                .endpointType("POST")
                .endpointUrl("/api/users/login")
                .jsonResponse(jsonResponse)
                .createdAt(LocalDateTime.of(2022,7,18,18,5))
                .build();

        // When
        EndpointHistoryDto dto = endpointHistoryMapper.entity2Dto(endpointHistoryEntity);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("POST", dto.getEndpointType());
        assertEquals("/api/users/login", dto.getEndpointUrl());
        assertEquals(LocalDateTime.of(2022,7,18,18,5), dto.getCreatedAt());
        assertEquals("\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE2NTgyMTcwNzUsImlhdCI6MTY1ODE5OTA3NX0.HYcUC8mO5ahVENOM8biono2SQLQDyO-ZiEPZ_DPFc7Hgtd0kiECsA-KtZtR6mpZjHbunwbY72QYma795jvcuEw\"", dto.getJsonResponse().get("token").toString());

    }

}
