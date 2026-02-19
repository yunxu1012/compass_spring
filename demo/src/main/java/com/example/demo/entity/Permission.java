package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Permission {
    @Id
    private Integer permissionId;
    
    @Column(unique = true, nullable = false)
    private String name;

    public Permission() {
		super();
	}

	public Permission(Integer permissionId, String name) {
		super();
		this.permissionId = permissionId;
		this.name = name;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}