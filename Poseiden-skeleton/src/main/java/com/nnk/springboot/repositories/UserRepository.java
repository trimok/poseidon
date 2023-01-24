package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nnk.springboot.domain.User;

/**
 * interface UserRepository
 * 
 * @author trimok
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * findByUsername
     * 
     * @param username : the name of the user
     * @return : the user
     */
    User findByUsername(String username);
}
