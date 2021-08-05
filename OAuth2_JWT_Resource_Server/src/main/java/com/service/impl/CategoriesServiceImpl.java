package com.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.repository.CategoriesRepository;
import com.service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	private CategoriesRepository categoriesRepository;

	public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {

		this.categoriesRepository = categoriesRepository;
	}

	@Override
	public void createCategoriesCollection() {
		
		categoriesRepository.createCategoriesCollection();
	}

	@Override
	public void dropCategoriesCollection() {
		
		categoriesRepository.dropCategoriesCollection();
	}

	@Override
	public void addCategory(String category) {
		
		categoriesRepository.addCategory(category);
	}

	@Override
	public void deleteCategory(String category) {
	
		categoriesRepository.deleteCategory(category);
	}

	@Override
	public List<String> findAllCategories() {
	
		
		return categoriesRepository.findCategories().getCategories();
	}

	@Override
	public void createCategory() {
		
		categoriesRepository.createCategory();
	}
}
