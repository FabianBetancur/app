package com.ids.app.persistence.mapper;

import com.ids.app.domain.UserDto;
import com.ids.app.persistence.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    List<UserDto> toUserDto(List<User> userList);
    @InheritInverseConfiguration
    User toUser(UserDto userDto);
}
