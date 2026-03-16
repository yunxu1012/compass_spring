package com.example.demo.entity;

public class CustomerSearch {
	private int squareFeet;
	private int price;
	private BedCount bedCount;
	private BathCount bathCount;
	private Integer cityId;
    private String homeType;
	
	public CustomerSearch() {
		super();
	}
	
	
	
	public CustomerSearch(int squareFeet, int price,  BedCount bedCount, BathCount bathCount, Integer cityId,
			String homeType) {
		super();
		this.squareFeet = squareFeet;
		this.price = price;
		this.bedCount = bedCount;
		this.bathCount = bathCount;
		this.cityId = cityId;
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

	public Integer getCityId() {
		return cityId;
	}

	public void setCity(Integer cityId) {
		this.cityId = cityId;
	}

	public String getHomeType() {
		return homeType;
	}

	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}

}
