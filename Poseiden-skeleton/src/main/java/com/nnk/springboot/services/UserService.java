package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
	return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
	return userRepository.save(user);
    }

    @Override
    public User findUserById(Integer id) {
	User user = userRepository.findById(id).orElse(null);
	return user;
    }

    @Override
    public User findUserByName(String username) {
	User user = userRepository.findByUsername(username);
	return user;
    }

    @Override
    public User updateUser(User user) {
	return userRepository.save(user);
    }

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
}