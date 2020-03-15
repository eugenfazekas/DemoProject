package com.demoproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.demoproject.entity.Role;

public interface RoleRepository extends CrudRepository <Role, Long> {

	Role findByRole(String role);
	
}
