package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;

public interface IUserService {

    List<User> findAllUsers();

    User addUser(User user);

    User findUserById(Integer id);

    User updateUser(User user);

    boolean deleteUser(Integer id);

    User findUserByName(String username);
}