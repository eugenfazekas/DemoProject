package com.service;

public interface MyCommandLineRunner {

	public void createCollectionUsers();
	
	public void dropCollectionUsers();
	
	public void createCollectionArticles();
	
	public void dropCollectionArticles();
	
	public void createCollectionCategories();
	
	public void dropCollectionCategories();
	
	public void createCategory();
	
	public void insertDummyCategories();
	
	public void importDummyJsonArticles();
	
}
