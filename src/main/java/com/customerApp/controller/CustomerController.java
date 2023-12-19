package com.customerApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.customerApp.entity.Customer;
import com.customerApp.entity.Attendees;
import com.customerApp.entity.Role;
import com.customerApp.exception.UserNotFoundException;
import com.customerApp.service.CustomerService;




@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	//method to list customer/admin


	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "firstName", "asc", null);
	}


	//method to list customer/admin by page num
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword
			) {
		System.out.println("Sort Field: " + sortField);
		System.out.println("Sort Order: " + sortDir);

		Page<Customer> page = customerService.listByPage(pageNum, sortField, sortDir, keyword);

		List<Customer> listCustomers = page.getContent();

		long startCount = (pageNum - 1) * CustomerService.CUSTOMER_PER_PAGE + 1;
		long endCount = startCount + CustomerService.CUSTOMER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listCustomers", listCustomers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);

		return "customers";
	}

	//method to save customer/admin
	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes)
			throws IOException {

			customerService.saveCustomer(customer);
			redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

			return getRedirectURLtoAffectedUser(customer);

	}
	
	@GetMapping("/customers/new")
	public String newUser(Model model) {
		List<Role> listRoles = customerService.listRoles();
		
		Customer customer = new Customer();
		customer.setEnabled(true);
		
		model.addAttribute("customer", customer);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		
		return "customer_form";
	}

	private String getRedirectURLtoAffectedUser(Customer customer) {
		String firstPartOfEmail = customer.getEmail().split("@")[0];
		return "redirect:/customers/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
	}
	
	
	@GetMapping("/customers/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) throws UserNotFoundException {
		Customer customer = customerService.get(id);
		List<Role> listRoles = customerService.listRoles();
		
		model.addAttribute("customer", customer);
		model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
		model.addAttribute("listRoles", listRoles);
		
		return "customer_form";
	}
	
	@GetMapping("/customers/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			customerService.delete(id);;
			redirectAttributes.addFlashAttribute("message", 
					"The user ID " + id + " has been deleted successfully");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		customerService.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/customers";
	}
	
	@GetMapping("/attendee_form")
	public String OpenTwilioPage(Model model) {
		Attendees attendee = new Attendees();
		
		model.addAttribute("attendee", attendee);
		return "Attendee_form";
	}
	

	@GetMapping("/sendInvite_form")
	public String sendInvite(Model model) {
		Attendees attendee = new Attendees();
		
		model.addAttribute("attendee", attendee);
		return "SendInvite_form";
	}

}




//	@PostMapping("/signup/save")
//	public String viewSignUpPage(Customer customer) {
//		customerService.registerCustomer(customer);
//		return "register_success";
//	}

//	@GetMapping("/create_customer")
//	public String createCustomer(Customer customer) {
////		customerService.registerCustomer(customer);
//
//		return "register";
//	}


