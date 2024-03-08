package com.mes.motorph.services;

import com.mes.motorph.entity.User;
import com.mes.motorph.exception.UserException;
import com.mes.motorph.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public List<User> fetchAllUsers() throws UserException {
        return userRepository.fetchAllUsers();
    }

    public User fetchUserDetail(String username) throws UserException {
        return userRepository.fetchUserDetail(username);
    }

    public User fetchUserDetailPasswordCreate(String username) throws UserException {
        return userRepository.fetchUserDetailPasswordReset(username);
    }

    public Optional<User> authenticateUser(String username, String password) throws UserException {
        // Fetch user details based on the provided username
        User user = userRepository.fetchUserDetail(username);

        if (user != null) {
            // Decrypt the stored password and check if it matches the provided password
            try {
                String decryptedPassword = PasswordService.decrypt(user.getHashPassword());
                if (password.equals(decryptedPassword)) {
                    // Authentication successful, return the user object
                    return Optional.of(user);
                }
            } catch (Exception e) {
                throw new UserException("Error decrypting password", e);
            }
        }

        // Authentication failed, return an empty optional
        return Optional.empty();
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
