package com.mes.motorph.services;

import com.mes.motorph.entity.Role;
import com.mes.motorph.exception.RoleException;
import com.mes.motorph.repository.RoleRepository;

import java.util.List;

public class RoleService {

    RoleRepository roleRepository = new RoleRepository();

    public List<Role> fetchAllRoles() throws RoleException {
        return roleRepository.fetchAllRoles();
    }

    public void createRole(String roleName) throws RoleException {
        roleRepository.createRole(roleName);
    }

    public void updateRole(Role role) throws RoleException {
        roleRepository.updateRole(role);
    }

    public void deleteRole(int roleId) throws RoleException {
        roleRepository.deleteRole(roleId);
    }

}
