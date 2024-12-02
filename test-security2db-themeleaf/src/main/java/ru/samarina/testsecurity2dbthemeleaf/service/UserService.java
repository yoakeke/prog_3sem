package ru.samarina.testsecurity2dbthemeleaf.service;

import org.springframework.stereotype.Service;
import ru.samarina.testsecurity2dbthemeleaf.dto.UserDto;
import ru.samarina.testsecurity2dbthemeleaf.entity.User;

import java.util.List;

@Service
public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

}