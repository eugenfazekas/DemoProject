package com.model;

import java.util.List;

public class User {

	private String id;
	private String firstName;
	private String lastName;
	private String fullName;
	private String date_registered;
	private String activeProfilePhoto;
	private List<String> articlesId;
	private List<String> profilePhotos;
	private Address address;

	
	public User() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFullName() {
		return fullName;
	}
	public void setFullName() {
		this.fullName = this.firstName+ " "+ this.lastName;
	}


	public String getDate_registered() {
		return date_registered;
	}


	public void setDate_registered(String date_registered) {
		this.date_registered = date_registered;
	}


	public String getActiveProfilePhoto() {
		return activeProfilePhoto;
	}


	public void setActiveProfilePhoto(String activeProfilePhoto) {
		this.activeProfilePhoto = activeProfilePhoto;
	}


	public List<String> getArticlesId() {
		return articlesId;
	}


	public void setArticlesId(List<String> articlesId) {
		this.articlesId = articlesId;
	}


	public List<String> getProfilePhotos() {
		return profilePhotos;
	}


	public void setProfilePhotos(List<String> profilePhotos) {
		this.profilePhotos = profilePhotos;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName=" + fullName
				+ ", date_registered=" + date_registered + ", activeProfilePhoto=" + activeProfilePhoto
				+ ", articlesId=" + articlesId + ", profilePhotos=" + profilePhotos + ", address=" + address + "]";
	}
}
