package com.demoproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.demoproject.entity.User;


public interface UserRepository extends CrudRepository <User,Long> {

	User findByEmail(String email);

	User findByActivation(String code);
	
}