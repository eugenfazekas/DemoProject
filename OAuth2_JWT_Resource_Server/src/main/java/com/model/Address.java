package com.model;

public class Address {
	
	private String country;
	private String city;
	private String street;
	private String number;	
	
	public Address() {
	}
	
	public Address(String country, String city, String street, String number) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Address [country=" + country + ", city=" + city + ", street=" + street + ", number=" + number + "]";
	}
}
