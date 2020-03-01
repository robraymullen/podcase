package com.podcase.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class AbstractRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	protected void persist(Object entity) {
		entityManager.persist(entity);
        entityManager.flush();
	}
	
	protected void update(Object entity) {
		entityManager.merge(entity);
		entityManager.flush();
	}

}
