package com.blurdel.sdjpa;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpa.domain.Book;
import com.blurdel.sdjpa.repository.BookRepository;

@ActiveProfiles("mysql")
@DataJpaTest
@ComponentScan(basePackages = {"com.blurdel.sdjpa.dao"})
//@Import(BookDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

	@Autowired
	BookRepository bookRepo;
	
	
	@Test
	void testEmptyResultException() {
		
		assertThrows(EmptyResultDataAccessException.class, () -> {
			Book book = bookRepo.readByTitle("foobar4");
		});
	}
	
	@Test
	void testNullParam() {		
		assertNull(bookRepo.getByTitle(null));
	}
	
	@Test
	void testNoException() {
		assertNull(bookRepo.getByTitle("foo"));
	}
	
}
