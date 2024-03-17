package com.ids.app.domain.repository;

import com.ids.app.domain.UserDto;
import org.mapstruct.control.MappingControl;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryDto {
    List<UserDto> getByColumns();
    List<UserDto> getAll();
    Optional<UserDto> getByEmail(String email);
    UserDto save(UserDto user);
    void delete(long id);
}
