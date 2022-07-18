package com.btorrelio.tenpoapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENDPOINT_HISTORIES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ENDPOINT_TYPE")
    private String endpointType;

    @Column(name = "ENDPOINT_URL")
    private String endpointUrl;

    @Column(name = "JSON_RESPONSE", length = 4000)
    private String jsonResponse;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

}
