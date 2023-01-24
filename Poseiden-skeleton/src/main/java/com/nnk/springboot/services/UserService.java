package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/**
 * 
 * The UserService
 * 
 * @author trimok
 */
@Service
public class UserService implements IUserService {

    /**
     * userRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Constructor
     * 
     * @param userRepository : userRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    /**
     * findAllUsers
     */
    @Override
    public List<User> findAllUsers() {
	return userRepository.findAll();
    }

    /**
     * addUser
     */
    @Override
    public User addUser(User user) {
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	user.setPassword(encoder.encode(user.getPassword()));
	return userRepository.save(user);
    }

    /**
     * findUserById
     */
    @Override
    public User findUserById(Integer id) {
	User user = userRepository.findById(id).orElse(null);
	return user;
    }

    /**
     * findUserByName
     */
    @Override
    public User findUserByName(String username) {
	User user = userRepository.findByUsername(username);
	return user;
    }

    /**
     * updateUser
     */
    @Override
    public User updateUser(User user) {
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	user.setPassword(encoder.encode(user.getPassword()));
	return userRepository.save(user);
    }

    /**
     * deleteUser
     */
    @Override
    public boolean deleteUser(Integer id) {
	User user = userRepository.findById(id).orElse(null);

	if (user != null) {
	    userRepository.delete(user);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * deleteAllUser
     */
    @Override
    public void deleteAllUser() {
	userRepository.deleteAll();
    }
}