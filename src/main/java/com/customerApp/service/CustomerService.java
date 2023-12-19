package com.customerApp.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.customerApp.entity.Customer;
import com.customerApp.entity.Role;
import com.customerApp.exception.UserNotFoundException;
import com.customerApp.repository.CustomerRepository;
import com.customerApp.repository.RoleRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CustomerService {

	public static final int CUSTOMER_PER_PAGE = 4;


	@Autowired private CustomerRepository customerRepo;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private RoleRepository roleRepo;


	//method to list customer in ascending order
	public List<Customer> listAll(){
		return customerRepo.findAll(Sort.by("firstName").ascending());
	}

	//method to list by pagenum, field , keyword, dir etc
	public Page<Customer> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMER_PER_PAGE, sort);

		if (keyword != null) {
			return customerRepo.findAll(keyword, pageable);
		}

		return customerRepo.findAll(pageable);
	}



	//method to save registered customer
	public Customer saveCustomer (Customer customer) {
		encodePassword(customer);
		return customerRepo.save(customer);
	}


	//method to encode password
	private void encodePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}


	//method to list role
	public List<Role> listRoles(){
		return roleRepo.findAll();
	}


	//method to enable user
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		customerRepo.updateEnabledStatus(id, enabled);
	}


	//method to delete user
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = customerRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
		customerRepo.deleteById(id);
		
		}

	public Customer get(Integer id) throws UserNotFoundException {
		try {
			return customerRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}

	//method for checking uniqueness of email
	public boolean isEmailUnique(Integer id, String email) {
		Customer userByEmail  = customerRepo.findByEmail(email);

		if (userByEmail == null) return true;

		boolean isCreatingNew = (id ==null);

		if(isCreatingNew) {
			if (userByEmail != null) return false;
		}else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;

	}




}
