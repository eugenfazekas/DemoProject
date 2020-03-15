package com.demoproject.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="blogs")
@Entity
public class Blogs {
  
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String blogger;
	
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private Date posted;

	public Blogs() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBlogger() {
		return blogger;
	}

	public void setBlogger(String blogger) {
		this.blogger = blogger;
	}

	@Override
	public String toString() {
		return "Blogs [id=" + id + ", blogger=" + blogger + ", title=" + title + ", content=" + content + ", posted="
				+ posted + "]";
	}

	
    
	
    
}
