package com.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.ArticleService;

@RestController
@RequestMapping("admin")
public class AdminController {
	
	private ArticleService articleService;
			
	public AdminController(ArticleService articleService) {

		this.articleService = articleService;
	}

	@RequestMapping(value = "deleteArticle", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteCategory(@RequestParam String articleId) {

		articleService.deleteArticle(articleId);
			return articleId;
	}
}
