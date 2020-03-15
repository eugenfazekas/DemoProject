package com.demoproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demoproject.entity.Blogs;


public interface BlogRepository extends CrudRepository <Blogs , Long >{

	Blogs findByBlogger(String blogger);
	
	List<Blogs> findByOrderByIdDesc();

	List<Blogs> findByContentIgnoreCaseLikeOrTitleIgnoreCaseLikeOrderByIdDesc(String Blog,String Blog1);

}
