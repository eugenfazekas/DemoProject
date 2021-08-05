package com.service;

import java.util.List;

public interface CategoriesService {
	
	public void createCategoriesCollection();
	
	public void dropCategoriesCollection();
	
	public void createCategory();
	
	public void addCategory(String category);
	
	public void deleteCategory(String category);
	
	public List<String> findAllCategories();

}
