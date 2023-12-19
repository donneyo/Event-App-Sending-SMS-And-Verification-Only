package com.customerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerApp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
