package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.blurdel.sdjpa.domain.Book;

public interface BookDao {
	
	List<Book> findAllBooksSortedByTitle(Pageable pageable);
	
	List<Book> findAllBooks(Pageable pageable);
	
	List<Book> findAllBooks(int pageSize, int offset);
	
	List<Book> findAllBooks();
	
	Book getById(Long id);
	
	Book findByTitle(String title);
		
	Book saveNew(Book book);
	
	Book update(Book book);
	
	void delete(Long id);
	
}
