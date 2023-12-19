package com.customerApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.customerApp.repository.CustomerRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private CustomerRepository repo;

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testDeleteUser() {
		Integer userId = 3;
		repo.deleteById(userId);
		
	}
	


	
	

//
//	@Test
//	public void testCreateNewUserWithOneRole() {
//		Role roleAdmin = entityManager.find(Role.class, 1);
//		User userNamHM = new User("nam@codejava.net", "nam2020", "Nam", "Ha Minh");
//		userNamHM.addRole(roleAdmin);
//
//		User savedUser = repo.save(userNamHM);
//
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
//
//	@Test
//	public void testCreateNewUserWithTwoRoles() {
//		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
//		Role roleEditor = new Role(3);
//		Role roleAssistant = new Role(5);
//
//		userRavi.addRole(roleEditor);
//		userRavi.addRole(roleAssistant);
//
//		User savedUser = repo.save(userRavi);
//
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
//
//	@Test
//	public void testListAllUsers() {
//		Iterable<Customer> listUsers = repo.findAll();
//		listUsers.forEach(user -> System.out.println(user));
//	}
//
//	@Test
//	public void testGetUserById() {
//		User userNam = repo.findById(1).get();
//		System.out.println(userNam);
//		assertThat(userNam).isNotNull();
//	}
//
//	@Test
//	public void testUpdateUserDetails() {
//		Customer userNeyo = repo.findById(2).get();
//		userNeyo.setEnabled(true);
//		userNeyo.setEmail("neyo@gmail.com");
//
//		repo.save(userNeyo);
//	}
//
//	@Test
//	public void testUpdateUserRoles() {
//		Customer userTony = repo.findById(1).get();
//		Role roleAssist = new Role(2);
//
//
////		userRavi.getRoles().remove(roleEditor);
//		userTony.addRole(roleAssist);
//
//		repo.save(userTony);
//	}
}
