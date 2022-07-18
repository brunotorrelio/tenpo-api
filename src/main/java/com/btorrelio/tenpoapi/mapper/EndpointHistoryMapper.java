package com.btorrelio.tenpoapi.mapper;

import com.btorrelio.tenpoapi.dto.EndpointHistoryDto;
import com.btorrelio.tenpoapi.entity.EndpointHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EndpointHistoryMapper {

    @Mapping(target = "jsonResponse", expression = "java(mapString2Json(entity))")
    EndpointHistoryDto entity2Dto(EndpointHistory entity);

    default JsonNode mapString2Json(EndpointHistory entity) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(entity.getJsonResponse());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
