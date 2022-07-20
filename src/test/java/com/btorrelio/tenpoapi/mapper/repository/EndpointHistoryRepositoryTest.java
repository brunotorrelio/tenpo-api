package com.btorrelio.tenpoapi.mapper.repository;

import com.btorrelio.tenpoapi.entity.EndpointHistory;
import com.btorrelio.tenpoapi.repository.EndpointHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EndpointHistoryRepositoryTest {

    @Autowired
    private EndpointHistoryRepository endpointHistoryRepository;

    @Test
    void given_pageableWithPageAndRow_when_findAllIsCalled_then_returnPage() {
        // Given
        Integer numberPage = 0;
        Integer rows = 10;

        // When
        Page<EndpointHistory> page = endpointHistoryRepository.findAll(PageRequest.of(numberPage, rows));

        // Then
        assertNotNull(page);
        assertEquals(3, page.getTotalPages());
        assertEquals(26, page.getTotalElements());
        assertEquals(10, page.getNumberOfElements());

    }



}
