package com.podcase.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.podcase.model.Podcast;
import com.podcase.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private TestEntityManager entityManager;
	
	User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setName("name");
		user.setPassword("password");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSingleUserByName() {
		String name = "rob";
		user.setName(name);
		persist(user);
		Optional<User> actualUser = userRepository.findByName(name);
		assertEquals(name, actualUser.get().getName());
	}
	
	private void persist(User user) {
		entityManager.persist(user);
        entityManager.flush();
	}

}
