package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {
	@Id
	private Integer roleId;
	private String name;


	public Role() {
		super();
	}


	public Role(Integer roleId, String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private Set<Permission> permissions = new HashSet<>();
	
	public Set<Permission> getPermissions(){
		return permissions;
	}

}
