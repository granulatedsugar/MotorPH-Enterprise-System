package com.mes.motorph.services;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.repository.UserRepository;

import java.util.List;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public List<User> fetchAllUsers() throws UserException {
        return userRepository.fetchAllUsers();
    }
}
