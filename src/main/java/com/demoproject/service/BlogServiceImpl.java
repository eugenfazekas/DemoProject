package com.demoproject.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoproject.entity.Blogs;
import com.demoproject.repository.BlogRepository;


@Service
public class BlogServiceImpl {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private BlogRepository blogRepository;
	
	@Autowired
	public BlogServiceImpl(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
		
	}

	public String registerBlogs(Blogs blogsToRegister) {
		
		blogsToRegister.setPosted(new Date());
		blogRepository.save(blogsToRegister);
		log.debug("ez a BlosgService Implbol "+ blogsToRegister.toString());
		return "ok";
	}

	@Autowired
	public BlogRepository getBlogRepository() {
		return blogRepository;
	}

	@Autowired
	public void setBlogRepository(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	public List<Blogs> getBlogs() {
		return blogRepository.findByOrderByIdDesc();
	}
	
	public List<Blogs> findBySearch(String search1) {
		return blogRepository.findByContentIgnoreCaseLikeOrTitleIgnoreCaseLikeOrderByIdDesc("%"+search1+"%","%"+search1+"%");
	}

	

}
