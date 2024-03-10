package com.ids.app.persistence.mapper;

import com.ids.app.domain.UserDto;
import com.ids.app.persistence.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-10T13:03:06-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.10 (Debian)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        if ( user.getUserId() != null ) {
            userDto.setUserId( user.getUserId() );
        }
        userDto.setUserName( user.getUserName() );
        userDto.setUserEmail( user.getUserEmail() );
        userDto.setUserPassword( user.getUserPassword() );

        return userDto;
    }

    @Override
    public List<UserDto> toUserDto(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( userList.size() );
        for ( User user : userList ) {
            list.add( toUserDto( user ) );
        }

        return list;
    }

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userDto.getUserId() );
        user.setUserName( userDto.getUserName() );
        user.setUserEmail( userDto.getUserEmail() );
        user.setUserPassword( userDto.getUserPassword() );

        return user;
    }
}
