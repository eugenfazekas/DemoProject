package com.model;

import java.util.List;

public class Category {

	private String id;
	private String category;
	private List<String> categories;

	public Category() {
	}
		
	public Category(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", category=" + category + ", categories=" + categories + "]";
	}

}
