package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpa.dao.BookDao;
import com.blurdel.sdjpa.domain.Book;


@ActiveProfiles("mysql")
@DataJpaTest
@ComponentScan(basePackages = {"com.blurdel.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoImplTest {

	@Autowired
	BookDao bookDao;
	
	
	@Test
	void testFindAllBookPage1_SortByTitle() {
		List<Book> books = bookDao.findAllBooksSortedByTitle(PageRequest.of(0, 10, 
				Sort.by(Sort.Order.desc("title"))));
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBookPage1_pageable() {
		List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10));
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBookPage2_pageable() {
		List<Book> books = bookDao.findAllBooks(PageRequest.of(1, 10));
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBookPage10_pageable() {
		List<Book> books = bookDao.findAllBooks(PageRequest.of(10, 10)); // If you exceed # rows in table, will get 0 recs back
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(0);
	}
	
	@Test
	void testFindAllBookPage1() {
		List<Book> books = bookDao.findAllBooks(10, 0);
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBookPage2() {
		List<Book> books = bookDao.findAllBooks(10, 10);
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(10);
	}
	
	@Test
	void testFindAllBookPage10() {
		List<Book> books = bookDao.findAllBooks(10, 100); // If you exceed # rows in table, will get 0 recs back
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isEqualTo(0);
	}
	
	@Test
	void testFindAllBooks() {
		List<Book> books = bookDao.findAllBooks();
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isGreaterThan(4);
	}
	
	@Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNew(book);

        bookDao.delete(saved.getId());

        // Verify an exception is thrown
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
        	bookDao.getById(saved.getId());
        });
        
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNew(book);

        saved.setTitle("New Book");
        bookDao.update(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
        
        Book saved = bookDao.saveNew(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }
    
}
