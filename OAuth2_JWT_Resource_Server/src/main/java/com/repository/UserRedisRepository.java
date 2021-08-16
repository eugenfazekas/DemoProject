package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository
public interface UserRedisRepository extends CrudRepository<User, String> {

}
