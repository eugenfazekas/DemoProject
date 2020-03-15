package com.demoproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.demoproject.entity.Index;


public interface IndexRepository extends CrudRepository <Index , Long >{

	
	List<Index> findByOrderByIdDesc();
	
	Index findByTitleOrderByIdDesc(String title);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from index where title = ?1 ",nativeQuery = true)
	void findByTitle1(String title);
	
	Index findByTitle(String title);
}
