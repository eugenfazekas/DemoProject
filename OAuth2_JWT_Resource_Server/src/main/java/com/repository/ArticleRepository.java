package com.repository;

import java.util.List;

import com.model.Article;

public interface ArticleRepository {
	
	public void createArticleCollection();
	
	public void dropArticleCollection();

	public void saveArticle(Article article);
	
	public List<Article> findAllArticles();

	public void insertAllArticles(List<Article> articles);
	
	public Article findArticleById(String articleId);
	
	public void deleteArticle(String articleId);
	
}
