package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.dto.PaginationDto;
import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.entity.EndpointHistory;
import com.btorrelio.tenpoapi.mapper.EndpointHistoryMapper;
import com.btorrelio.tenpoapi.repository.EndpointHistoryRepository;
import com.btorrelio.tenpoapi.service.impl.EndpointHistoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EndpointHistoryServiceImpl.class)
class EndpointHistoryServiceTest {

    @Autowired
    private EndpointHistoryService endpointHistoryService;

    @MockBean
    private EndpointHistoryRepository endpointHistoryRepositoryMock;

    @MockBean
    private EndpointHistoryMapper endpointHistoryMapperMock;

    @Test
    void given_uriAndMethodAndString_when_saveIsCalled_then_returnDto() {
        // Given
        String uri = "/api/users/login";
        String method = "POST";
        String jsonResponse = "{\n  \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE2NTgyMTcwNzUsImlhdCI6MTY1ODE5OTA3NX0.HYcUC8mO5ahVENOM8biono2SQLQDyO-ZiEPZ_DPFc7Hgtd0kiECsA-KtZtR6mpZjHbunwbY72QYma795jvcuEw\",\n  \"timeStamp\": \"2022-07-18T23:51:15.52\"\n}";

        EndpointHistoryDto endpointHistoryDto = EndpointHistoryDto.builder()
                .id(1L)
                .endpointType("POST")
                .endpointUrl("/api/users/login")
                .jsonResponse(stringToJsonNode(jsonResponse))
                .createdAt(LocalDateTime.of(2022,7,18,18,5))
                .build();

        EndpointHistory endpointHistoryEntity = EndpointHistory.builder()
                .id(1L)
                .endpointType("POST")
                .endpointUrl("/api/users/login")
                .jsonResponse(jsonResponse)
                .createdAt(LocalDateTime.of(2022,7,18,18,5))
                .build();

        when(endpointHistoryRepositoryMock.save(any())).thenReturn(endpointHistoryEntity);
        when(endpointHistoryMapperMock.entity2Dto(any())).thenReturn(endpointHistoryDto);
        // When
        EndpointHistoryDto endpointHistoryDtoActual = endpointHistoryService.save(uri,method,jsonResponse);

        // Then
        assertNotNull(endpointHistoryDtoActual);
        assertEquals(1L,endpointHistoryDtoActual.getId());
        assertEquals("POST", endpointHistoryDtoActual.getEndpointType());
        assertEquals("/api/users/login", endpointHistoryDtoActual.getEndpointUrl());
        assertEquals(stringToJsonNode(jsonResponse), endpointHistoryDtoActual.getJsonResponse());
        assertEquals(LocalDateTime.of(2022,7,18,18,5), endpointHistoryDtoActual.getCreatedAt());

    }

    @Test
    void given_pageAndRows_when_getAllIsCalled_then_returnPaginationDto() {
        // Given
        Integer page = 0;
        Integer rows = 10;
        String jsonResponse1 = "{\n  \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmciLCJleHAiOjE2NTgyMTcwNzUsImlhdCI6MTY1ODE5OTA3NX0.HYcUC8mO5ahVENOM8biono2SQLQDyO-ZiEPZ_DPFc7Hgtd0kiECsA-KtZtR6mpZjHbunwbY72QYma795jvcuEw\",\n  \"timeStamp\": \"2022-07-18T23:51:15.52\"\n}";

        EndpointHistory endpointHistoryEntity1 = EndpointHistory.builder()
                .id(1L)
                .endpointType("POST")
                .endpointUrl("/api/user/login")
                .createdAt(LocalDateTime.of(2020,7,18,15,20))
                .jsonResponse(jsonResponse1)
                .build();

        EndpointHistory endpointHistoryEntity2 = EndpointHistory.builder()
                .id(2L)
                .endpointType("POST")
                .endpointUrl("/api/user/singoff")
                .createdAt(LocalDateTime.of(2020,7,18,15,30))
                .jsonResponse(null)
                .build();

        EndpointHistoryDto endpointHistoryDto1 = EndpointHistoryDto.builder()
                .id(1L)
                .endpointType("POST")
                .endpointUrl("/api/user/login")
                .createdAt(LocalDateTime.of(2020,7,18,15,20))
                .jsonResponse(stringToJsonNode(jsonResponse1))
                .build();

        EndpointHistoryDto endpointHistoryDto2 = EndpointHistoryDto.builder()
                .id(2L)
                .endpointType("POST")
                .endpointUrl("/api/user/singoff")
                .createdAt(LocalDateTime.of(2020,7,18,15,30))
                .jsonResponse(null)
                .build();

        Page<EndpointHistory> pageEndpointHistoryList = new PageImpl<EndpointHistory>(List.of(endpointHistoryEntity1, endpointHistoryEntity2), PageRequest.of(page,rows),2);

        when(endpointHistoryRepositoryMock.findAll(PageRequest.of(page,rows))).thenReturn(pageEndpointHistoryList);
        when(endpointHistoryMapperMock.entity2Dto(endpointHistoryEntity1)).thenReturn(endpointHistoryDto1);
        when(endpointHistoryMapperMock.entity2Dto(endpointHistoryEntity2)).thenReturn(endpointHistoryDto2);

        // When
        PaginationDto<EndpointHistoryDto> paginationDto = endpointHistoryService.getAll(page,rows);

        // Then
        assertNotNull(paginationDto);
        assertEquals(0, paginationDto.getPage());
        assertEquals(10, paginationDto.getRows());
        assertEquals(2, paginationDto.getTotalElements());
        assertEquals(1, paginationDto.getTotalPages());
        assertEquals(2, paginationDto.getData().size());
        assertEquals(1L, paginationDto.getData().get(0).getId());
        assertEquals("POST", paginationDto.getData().get(0).getEndpointType());
        assertEquals("/api/user/login", paginationDto.getData().get(0).getEndpointUrl());
        assertEquals(LocalDateTime.of(2020,7,18,15,20), paginationDto.getData().get(0).getCreatedAt());
        assertEquals(stringToJsonNode(jsonResponse1), paginationDto.getData().get(0).getJsonResponse());
        assertEquals(2L, paginationDto.getData().get(1).getId());
        assertEquals("POST", paginationDto.getData().get(1).getEndpointType());
        assertEquals("/api/user/singoff", paginationDto.getData().get(1).getEndpointUrl());
        assertEquals(LocalDateTime.of(2020,7,18,15,30), paginationDto.getData().get(1).getCreatedAt());
        assertNull(paginationDto.getData().get(1).getJsonResponse());

    }

    private JsonNode stringToJsonNode(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
