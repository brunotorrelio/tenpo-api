package com.btorrelio.tenpoapi.service.impl;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.dto.PaginationDto;
import com.btorrelio.tenpoapi.entity.EndpointHistory;
import com.btorrelio.tenpoapi.mapper.EndpointHistoryMapper;
import com.btorrelio.tenpoapi.repository.EndpointHistoryRepository;
import com.btorrelio.tenpoapi.service.EndpointHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EndpointHistoryServiceImpl implements EndpointHistoryService {

    private final EndpointHistoryRepository endpointHistoryRepository;

    private final EndpointHistoryMapper endpointHistoryMapper;

    @Autowired
    public EndpointHistoryServiceImpl(EndpointHistoryRepository endpointHistoryRepository, EndpointHistoryMapper endpointHistoryMapper) {
        this.endpointHistoryRepository = endpointHistoryRepository;
        this.endpointHistoryMapper = endpointHistoryMapper;
    }

    @Override
    @Transactional
    public EndpointHistoryDto save(String uri, String method, String response) {
        log.info("Saving endpoint history with uri: {} method: {}", uri, method);
        EndpointHistory endpointHistory = new EndpointHistory();
        endpointHistory.setEndpointType(method);
        endpointHistory.setEndpointUrl(uri);
        endpointHistory.setJsonResponse(response);
        endpointHistory.setCreatedAt(LocalDateTime.now());
        return endpointHistoryMapper.entity2Dto(endpointHistoryRepository.save(endpointHistory));
    }

    @Override
    public PaginationDto<List<EndpointHistoryDto>> getAll(Integer page, Integer rows) {
        log.info("getting endpoints history with page: {} and rows: {}", page, rows);
        Page<EndpointHistory> result = endpointHistoryRepository.findAll(PageRequest.of(page, rows));

        PaginationDto<List<EndpointHistoryDto>> dto = new PaginationDto<>();
        dto.setPage(page);
        dto.setRows(rows);
        dto.setTotalPages(result.getTotalPages());
        dto.setTotalElements(result.getTotalElements());
        dto.setData(result.get().map(endpointHistoryMapper::entity2Dto).collect(Collectors.toList()));

        return dto;
    }

}
