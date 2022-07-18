package com.btorrelio.tenpoapi.repository;

import com.btorrelio.tenpoapi.entity.EndpointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EndpointHistoryRepository extends PagingAndSortingRepository<EndpointHistory, Long> {

    Page<EndpointHistory> findAll(Pageable pageable);

}
