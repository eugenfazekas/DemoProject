package com.model;

public class Article {

	private String id;
	private String category;
	private String title;
	private String owner;
	private String published_date;
	private String content;
	private String image_title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPublished_date() {
		return published_date;
	}

	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage_title() {
		return image_title;
	}

	public void setImage_title(String image_title) {
		this.image_title = image_title;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", category=" + category + ", title=" + title + ", owner=" + owner
				+ ", published_date=" + published_date + ", content=" + content + ", image_title=" + image_title + "]";
	}

}
