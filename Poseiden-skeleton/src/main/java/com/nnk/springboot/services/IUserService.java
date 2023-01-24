package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;

/**
 * interface IUserService
 * 
 * @author trimok
 */
public interface IUserService {

    /**
     * findAllUsers
     * 
     * @return : the list of all User
     */
    List<User> findAllUsers();

    /**
     * addUser
     * 
     * @param user : the User to be added
     * @return : the added User
     */
    User addUser(User user);

    /**
     * findUserById
     * 
     * @param id : the id of the User to be found
     * @return : the found User
     */
    User findUserById(Integer id);

    /**
     * updateUser
     * 
     * @param user : the updated User
     * @return : the updated User in the databse
     */
    User updateUser(User user);

    /**
     * deleteUser
     * 
     * @param id : the id of the User to be deleted
     * @return : true if deleted, false if not deleted
     */
    boolean deleteUser(Integer id);

    /**
     * findUserByName
     * 
     * @param username : the name of the user
     * @return : the User with corresponding username
     */
    User findUserByName(String username);

    /**
     * deleteAllUser
     */
    void deleteAllUser();
}