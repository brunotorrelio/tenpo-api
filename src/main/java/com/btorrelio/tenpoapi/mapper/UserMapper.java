package com.btorrelio.tenpoapi.mapper;

import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto entity2Dto(User entity);

    User dto2Entity(UserDto dto);

}
