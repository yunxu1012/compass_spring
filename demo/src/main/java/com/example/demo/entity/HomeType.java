package com.example.demo.entity;

public enum Hometype {
	HOUSE("House"), 
	MULTI_FAMILY("Multi Family"), 
	TOWNHOUSE("Townhouse"), 
	CONDO("Condo"), 
	MOBILE("Mobile"),
	CO_OP("Co-op"), 
	LAND("Land"),
	OTHER("Other");
	private String type;
	Hometype(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	
	public static Hometype fromType(String type) {
        for (Hometype homeType : Hometype.values()) {
            if (homeType.getType().equals(type)) {
                return homeType;
            }
        }
        throw new IllegalArgumentException("No enum constant with type " + type);
    }
}
