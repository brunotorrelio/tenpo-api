package com.btorrelio.tenpoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto <T> {

    private Integer page;

    private Integer rows;

    private T data;

    private Long totalElements;

    private Integer totalPages;

}
