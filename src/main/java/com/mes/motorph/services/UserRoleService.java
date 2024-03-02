package com.mes.motorph.services;

import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.UserRoleException;
import com.mes.motorph.repository.UserRoleRepository;

import java.util.List;

public class UserRoleService {

    UserRoleRepository userRoleRepository = new UserRoleRepository();

    public List<UserRole> fetchAllUserRoles() throws UserRoleException {
        return userRoleRepository.fetchAllUserRoles();
    }

    public List<UserRole> fetchUserRole(String username) throws UserRoleException {
        return userRoleRepository.fetchUserRoles(username);
    }

    public void createNewUserRole(UserRole userRole) throws UserRoleException {
        userRoleRepository.createUserRole(userRole);
    }

    public void updateUserRole(UserRole userRole) throws UserRoleException {
        userRoleRepository.updateUserRole(userRole);
    }

    public void deleteUserRole(UserRole userRole) throws UserRoleException {
        userRoleRepository.deleteUserRole(userRole);
    }
}
