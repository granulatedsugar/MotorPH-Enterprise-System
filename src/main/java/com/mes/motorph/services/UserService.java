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

    public void createNewUser(User user) throws UserException {
        userRepository.createNewUser(user);
    }

    public void updateUser(User user) throws UserException {
        userRepository.updateUser(user);
    }

    public void deleteUser(String username) throws UserException {
        userRepository.deleteUser(username);
    }
}
