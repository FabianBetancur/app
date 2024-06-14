package com.ids.app.persistence;

import com.ids.app.domain.UserDto;
import com.ids.app.domain.repository.UserRepositoryDto;
import com.ids.app.persistence.crud.UserCrudRepository;
import com.ids.app.persistence.entity.User;
import com.ids.app.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UserRepositoryDto {
    private final UserCrudRepository userCrudRepository;
    private final UserMapper mapper;
    @Autowired
    public UserRepository(UserCrudRepository userCrudRepository,UserMapper userMapper){
        this.userCrudRepository = userCrudRepository;
        this.mapper = userMapper;
    }

    @Override
    public List<UserDto> getByColumns() {
        List<User> userList = userCrudRepository.getByColumns().get();
        return mapper.toUserDto(userList);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> userList = userCrudRepository.findAll();
        return mapper.toUserDto(userList);
    }

    @Override
    public Optional<UserDto> getByEmail(String email) {
        return userCrudRepository.findByUserEmail(email)
                .map(mapper::toUserDto);
    }
    @Override
    public UserDto save(UserDto obj) {
        User dto = mapper.toUser(obj);
        return mapper.toUserDto(userCrudRepository.save(dto));
    }
    public boolean update(UserDto userDto){
        Optional<User> user  = userCrudRepository.findById(userDto.getUserId());
        if(user.isPresent()){
            user.get().setUserName(userDto.getUserName());
            user.get().setUserEmail(userDto.getUserEmail());
            mapper.toUserDto(userCrudRepository.save(user.get()));
            return true;
        }
        return false;
    }
    @Override
    public void delete(long id) {
        userCrudRepository.deleteById(id);
    }
    public Optional<UserDto> getById(long id){
        return userCrudRepository.findById(id)
                .map(mapper::toUserDto);
    }

}
