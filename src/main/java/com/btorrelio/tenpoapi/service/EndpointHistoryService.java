package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.dto.PaginationDto;


public interface EndpointHistoryService {

    EndpointHistoryDto save(String uri, String method, String response);

    PaginationDto<EndpointHistoryDto> getAll(Integer page, Integer rows);

}
