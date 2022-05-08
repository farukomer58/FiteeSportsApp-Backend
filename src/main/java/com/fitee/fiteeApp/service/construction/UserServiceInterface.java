package com.fitee.fiteeApp.service.construction;

import com.fitee.fiteeApp.model.RoleEntity;
import com.fitee.fiteeApp.model.User;

import java.util.List;

public interface UserServiceInterface {
    User saveUser(User user);
    RoleEntity saveRole(RoleEntity role);
    void addRoleToUser(String email, String roleName);
    User getUserByMail(String email);
    List<User> getUsers();
}
