package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hibernate.proxy.EntityNotFoundDelegate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpa.dao.AuthorDao;
import com.blurdel.sdjpa.dao.AuthorDaoImpl;
import com.blurdel.sdjpa.domain.Author;

import jakarta.persistence.EntityNotFoundException;

@ActiveProfiles("mysql")
@DataJpaTest
//@ComponentScan(basePackages = {"com.blurdel.sdjpajdbc.dao"})
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

	@Autowired
	AuthorDao authorDao;
	
	
//	@Test
//	void testFindAllAuthors() {
//		List<Author> authors = authorDao.findAll();
//		
//		assertThat(authors).isNotNull();
//		assertThat(authors.size()).isGreaterThan(0);		
//	}
	
	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		authorDao.delete(saved.getId());
		
		assertThrows(JpaObjectRetrievalFailureException.class, () -> {
			authorDao.findById(saved.getId());
		});
		
	}
	
	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		saved.setLastName("Thompson");
		authorDao.update(saved);
		
		Author fetched = authorDao.findById(saved.getId());
		
		assertThat(fetched.getLastName()).isEqualTo("Thompson");
	}
	
	@Test
	void testSaveAuthor() {
		Author author = new Author();
		author.setFirstName("John");
		author.setLastName("thompson");
		Author saved = authorDao.saveNew(author);
		
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull(); // If no commit or transaction, id will be null here...
	}
	
	@Test
	void testFindAuthorByName() {
		Author author = authorDao.findByName("Craig", "Walls");
		
		assertThat(author).isNotNull();
		
//		assertThrows(EmptyResultDataAccessException.class, () -> {
//			authorDao.getByName("David", "Anderson");
//		});
	}
	
	@Test
	void testGetAuthorByNameNotFound() {
		assertThrows(EntityNotFoundException.class, () -> {
			Author author = authorDao.findByName("foo", "bar");
		});
	}
	
	@Test
	void testFindAuthorById() {
		Author author = authorDao.findById(1L);
		assertThat(author).isNotNull();
	}

}
