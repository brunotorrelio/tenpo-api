package com.btorrelio.tenpoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationDto <T> {

    private Integer page;

    private Integer rows;

    private List<T> data;

    private Long totalElements;

    private Integer totalPages;

}
