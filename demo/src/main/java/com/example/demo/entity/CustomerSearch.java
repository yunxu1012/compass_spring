package com.example.demo.entity;

public class CustomerSearch {
	private int squareFeet;
	private int price;
	private BedCount bedCount;
	private BathCount bathCount;
	private String city;
    private String homeType;
	
	public CustomerSearch() {
		super();
	}
	
	
	
	public CustomerSearch(int squareFeet, int price,  BedCount bedCount, BathCount bathCount, String city,
			String homeType) {
		super();
		this.squareFeet = squareFeet;
		this.price = price;
		this.bedCount = bedCount;
		this.bathCount = bathCount;
		this.city = city;
		this.homeType = homeType;
	}



	public int getSquareFeet() {
		return squareFeet;
	}

	public void setSquareFeet(int squareFeet) {
		this.squareFeet = squareFeet;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public BedCount getBedCount() {
		return bedCount;
	}

	public void setBedCount(BedCount bedCount) {
		this.bedCount = bedCount;
	}

	public BathCount getBathCount() {
		return bathCount;
	}

	public void setBathCount(BathCount bathCount) {
		this.bathCount = bathCount;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHomeType() {
		return homeType;
	}

	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}

}
