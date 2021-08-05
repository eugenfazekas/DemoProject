package com.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model.Article;
import com.repository.ArticleRepository;
import com.repository.UserRepository;
import com.service.ArticleService;


@Service
public class ArticleServiceImpl implements ArticleService{

	private ArticleRepository articleRepository;
	private UserRepository userRepository;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
		this.articleRepository = articleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void createArticleCollection() {
		
		articleRepository.createArticleCollection();
		
	}

	@Override
	public void dropArticleCollection() {
		
		articleRepository.dropArticleCollection();
		
	}

	@Override
	public void saveArticle(Article article) {

		String pattern = "yyyy.MM.dd HH:mm:ss";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    UUID uuid = UUID.randomUUID();
		 
		userRepository.addArticle(article.getOwner(), uuid.toString());
		article.setId(uuid.toString());
		article.setPublished_date(simpleDateFormat.format(new Date()));
		articleRepository.saveArticle(article);
		
	}

	@Override
	public List<Article> findAllArticles() {

		return articleRepository.findAllArticles();
	}

	@Override
	public void insertAllArticles(List<Article> articles) {
		
		articleRepository.insertAllArticles(articles);	
	}

	@Override
	public void saveImage(MultipartFile file) {
		
		String title = file.getOriginalFilename();
		String[] userIdInput = title.split("Ω");
		String userId = userRepository.findUserById(userIdInput[0]).getId();

		    try { 
		    	InputStream bis = file.getInputStream();
			    BufferedImage bImage2 = ImageIO.read(bis);
				ImageIO.write(bImage2, "png", new File("src/main/resources/static/user/" + userId + "/" + title+ ".png") );
			} catch (IOException e) {
			log.debug("Erorr on saving imgae"+e.toString());
				e.printStackTrace();
			}
	}

	@Override
	public void deleteArticle(String articleId) {
		
		Article articleForDelete = findArticleById(articleId);
		articleRepository.deleteArticle(articleId);
		String delete =  articleForDelete.getImage_title() != null ? deleteImage(articleForDelete.getImage_title()) : "";	
		log.debug(delete);
	}

	@Override
	public Article findArticleById(String articleId) {
	
		return articleRepository.findArticleById(articleId);
	}

	@Override
	public String deleteImage(String imageName) {

		String[] imageNameParts = imageName.split("Ω");
		String userId = imageNameParts[0];
		String response = null;
		 File myObj = new File("src/main/resources/static/user/" + userId + "/" + imageName + ".png"); 
		    if (myObj.delete()) { 
		    	response = "Deleted the file: " + myObj.getName();
		    } else {
		    	response = "Failed to delete the file.";
		    }
		return response;
	}
}
