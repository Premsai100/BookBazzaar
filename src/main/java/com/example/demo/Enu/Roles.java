package com.example.demo.Enu;

import java.util.Set;

public enum Roles {
		ADMIN(Set.of(Permissions.READ,Permissions.DELETE,Permissions.UPDATE)),
		USER(Set.of(Permissions.READ));
	
	private final Set<Permissions> permission;

	public Set<Permissions> getPermission() {
		return permission;
	}

	Roles(Set<Permissions> permission) {
		this.permission = permission;
	}
}
