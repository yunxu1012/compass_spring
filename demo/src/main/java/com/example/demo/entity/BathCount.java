package com.example.demo.entity;

public enum BathCount {
    ONE("1"),
    ONE_AND_HALF("1.5"),
    TWO("2"),
    TWO_AND_HALF("2.5"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    NO_MAX("100");
	private String count;
	BathCount(String count) {
		this.count = count;
	}
	public String getCount() {
		return this.count;
	}
	
	public static BathCount fromCount(String count) {
        for (BathCount bathCount : BathCount.values()) {
            if (bathCount.getCount().equals(count)) {
                return bathCount;
            }
        }
        throw new IllegalArgumentException("No enum constant with count " + count);
    }
}
