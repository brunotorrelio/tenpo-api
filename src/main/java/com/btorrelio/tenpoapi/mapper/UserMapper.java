package com.btorrelio.tenpoapi.mapper;

import com.btorrelio.tenpoapi.dto.UserDto;
import com.btorrelio.tenpoapi.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto entity2Dto(User entity);

    User dto2Entity(UserDto dto);

}
