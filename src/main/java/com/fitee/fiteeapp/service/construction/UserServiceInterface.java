package com.fitee.fiteeapp.service.construction;

import com.fitee.fiteeapp.model.RoleEntity;
import com.fitee.fiteeapp.model.User;

import java.util.List;

public interface UserServiceInterface {
    User saveUser(User user);
    RoleEntity saveRole(RoleEntity role);
    void addRoleToUser(String email, String roleName);
    User getUserByMail(String email);
    List<User> getUsers();
}
