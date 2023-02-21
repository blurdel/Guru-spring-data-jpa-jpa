package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

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
	void testJpaNamedQuery() {
		Book book = bookRepo.jpaNamed("Clean Code");
		assertThat(book).isNotNull();		
	}
	
	@Test
	void testBookQueryNative() {
		Book book = bookRepo.findBookByTitleNativeQuery("Clean Code");
		assertThat(book).isNotNull();
	}
	
	@Test
	void testBookQueryNamed() {
		Book book = bookRepo.findBookByTitleWithQueryNamed("Clean Code");
		assertThat(book).isNotNull();
	}
	
	@Test
	void testBookQuery() {
		Book book = bookRepo.findBookByTitleWithQuery("Clean Code");
		assertThat(book).isNotNull();
	}
	
	@Test
	void testBookFuture() throws InterruptedException, ExecutionException {
		Future<Book> bookFuture = bookRepo.queryByTitle("Clean Code");
		
		Book book = bookFuture.get();
		
		assertNotNull(book);
	}
	
	@Test
	void testBookStream() {
		AtomicInteger count = new AtomicInteger();
		
		bookRepo.findAllByTitleNotNull().forEach(book -> {
			count.incrementAndGet();
		});
		
		assertThat(count.get()).isGreaterThan(4);
	}
	
	// NOTE: these test(s) required adding package-info.java to repositories to include: @org.springframework.lang.NonNullApi
	// Tells Spring to implement the null behavior for any repository in the package
	// Otherwise a unit test fails
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
