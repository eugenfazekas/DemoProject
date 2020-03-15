package com.demoproject.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="index")
@Entity
public class Index {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content1;
	
	@Column(columnDefinition = "TEXT")
	private String content2;
	
	@Column(columnDefinition = "TEXT")
	private String content3;
	
	private String video1;
	
	private String image1;

	private Date created;

	public Index() {
		
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent1() {
		return content1;
	}


	public void setContent1(String content1) {
		this.content1 = content1;
	}


	public String getContent2() {
		return content2;
	}


	public void setContent2(String content2) {
		this.content2 = content2;
	}


	public String getContent3() {
		return content3;
	}


	public void setContent3(String content3) {
		this.content3 = content3;
	}


	public String getVideo1() {
		return video1;
	}


	public void setVideo1(String video1) {
		this.video1 = video1;
	}


	public String getImage1() {
		return image1;
	}


	public void setImage1(String image1) {
		this.image1 = image1;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Index [id=" + id + ", title=" + title + ", content1=" + content1 + ", content2=" + content2
				+ ", content3=" + content3 + ", video1=" + video1 + ", image1=" + image1 + ", created=" + created + "]";
	}
	
}
