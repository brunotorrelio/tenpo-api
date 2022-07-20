package com.btorrelio.tenpoapi.controller;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.dto.PaginationDto;
import com.btorrelio.tenpoapi.dto.ResponseLoginDto;
import com.btorrelio.tenpoapi.service.EndpointHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EndpointHistoryControllerTest extends AbstractControllerTest {

    private final String BASE_URL_API = "/history";

    @MockBean
    private EndpointHistoryService endpointHistoryServiceMock;

    @Test
    @WithMockUser
    void given_pageAndRows_when_getAllIsCalledAndHasResults_then_returnPaginatedListAndStatusOk() throws Exception{

        ResponseLoginDto responseLoginDto = ResponseLoginDto.builder()
                .token("123456789abcdefghijklmnopqrstuvwxyz")
                .timeStamp(LocalDateTime.of(2022,7,18,10,5))
                .build();

        EndpointHistoryDto endpointHistoryDto1 = EndpointHistoryDto.builder()
                .id(1L)
                .endpointType("POST")
                .endpointUrl("/api/user/login")
                .createdAt(LocalDateTime.of(2020,7,18,15,20))
                .jsonResponse(super.objectToJsonNode(responseLoginDto))
                .build();

        EndpointHistoryDto endpointHistoryDto2 = EndpointHistoryDto
                .builder()
                .id(2L)
                .endpointType("POST")
                .endpointUrl("/api/user/singoff")
                .createdAt(LocalDateTime.of(2020,7,18,15,20))
                .jsonResponse(null)
                .build();

        PaginationDto<EndpointHistoryDto> paginationDto = PaginationDto.<EndpointHistoryDto>builder()
                .page(0)
                .rows(10)
                .data(List.of(endpointHistoryDto1,endpointHistoryDto2))
                .totalPages(1)
                .totalElements(2L)
                .build();

        when(endpointHistoryServiceMock.getAll(any(),any())).thenReturn(paginationDto);

        mockMvc.perform(get(BASE_URL_API)
                .queryParam("page","0")
                .queryParam("rows","10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.rows").value(10))
                .andExpect(jsonPath("$.data.size()").value(2))
                .andExpect(jsonPath("$.data[0].jsonResponse.token").value("123456789abcdefghijklmnopqrstuvwxyz"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @WithMockUser
    void given_pageAndRows_when_getAllIsCalledAndHasNotResults_then_returnPaginatedListAndStatusOk() throws Exception{

        PaginationDto<EndpointHistoryDto> paginationDto = PaginationDto.<EndpointHistoryDto>builder()
                .page(0)
                .rows(10)
                .data(new ArrayList<>())
                .totalPages(1)
                .totalElements(0L)
                .build();

        when(endpointHistoryServiceMock.getAll(any(),any())).thenReturn(paginationDto);

        mockMvc.perform(get(BASE_URL_API)
                        .queryParam("page","0")
                        .queryParam("rows","10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.rows").value(10))
                .andExpect(jsonPath("$.data.size()").value(0))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    @WithMockUser
    void given_pageButNotRows_when_getAllIsCalledAndHasResults_then_returnBadRequest() throws Exception{

        mockMvc.perform(get(BASE_URL_API)
                        .queryParam("page","0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void given_rowsButNotPages_when_getAllIsCalledAndHasResults_then_returnBadRequest() throws Exception{

        mockMvc.perform(get(BASE_URL_API)
                        .queryParam("rows","10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_pagesAndRowsWithoutAuthentication_when_getAllIsCalledAndHasResults_then_returnUnauthorized() throws Exception{

        mockMvc.perform(get(BASE_URL_API)
                        .queryParam("page","0")
                        .queryParam("rows","10"))
                .andExpect(status().isUnauthorized());
    }

}
