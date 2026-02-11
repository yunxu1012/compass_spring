package com.example.demo.entity;

public enum BedCount {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    NO_MAX("100");
	private String count;
	BedCount(String count) {
		this.count = count;
	}
	public String getCount() {
		return this.count;
	}
	
	public static BedCount fromCount(String count) {
        for (BedCount bedCount : BedCount.values()) {
            if (bedCount.getCount().equals(count)) {
                return bedCount;
            }
        }
        throw new IllegalArgumentException("No enum constant with count " + count);
    }
}
