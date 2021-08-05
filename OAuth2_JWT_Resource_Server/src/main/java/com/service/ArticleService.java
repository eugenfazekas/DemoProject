package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.model.Article;



public interface ArticleService {
	
	public void createArticleCollection();
	
	public void dropArticleCollection();

	public void saveArticle(Article article);
	
	public List<Article> findAllArticles();
	
	public void insertAllArticles(List<Article> articles);
	
	public void saveImage(MultipartFile image);
	
	public void deleteArticle(String articleTitle);
	
	public Article findArticleById(String articleId);	
	
	public String deleteImage(String imageName);
}
