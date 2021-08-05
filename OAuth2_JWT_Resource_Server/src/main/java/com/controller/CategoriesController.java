package com.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.CategoriesService;


@RestController
@RequestMapping("categories")
public class CategoriesController {
	
	private CategoriesService categoriesService;
	
	public CategoriesController(CategoriesService categoriesService) {
		
		this.categoriesService = categoriesService;
	}

	@RequestMapping(value = "findCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> findCategories() {
	
	
		return categoriesService.findAllCategories();
	}
	
	@RequestMapping(value = "addCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addCategory(@RequestParam String category) {

		categoriesService.addCategory(category);
			return category;
	}
	
	@RequestMapping(value = "deleteCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteCategory(@RequestParam String category) {

		categoriesService.deleteCategory(category);
			return category;
	}
}
