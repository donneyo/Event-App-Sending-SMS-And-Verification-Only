package com.customerApp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.customerApp.entity.Customer;
import com.customerApp.entity.Role;

public class CustomerUserDetails implements UserDetails {

	private Customer customer;

	public CustomerUserDetails(Customer customer) {
		this.customer = customer;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = customer.getRoles();

		List<SimpleGrantedAuthority> authories = new ArrayList<>();

		for (Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
		}

		return authories;
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return customer.isEnabled();
	}

	public String getFullname() {
		return this.customer.getFirstName() + " " + this.customer.getLastName();

	}


}
