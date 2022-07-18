package com.btorrelio.tenpoapi.service;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.dto.PaginationDto;

import java.util.List;

public interface EndpointHistoryService {

    EndpointHistoryDto save(String uri, String method, String response);

    PaginationDto<List<EndpointHistoryDto>> getAll(Integer page, Integer rows);

}
