package com.blurdel.sdjpa.dao;

import java.util.List;

import com.blurdel.sdjpa.domain.Book;

public interface BookDao {
	
	List<Book> findAllBooks();
	
	Book getById(Long id);
	
	Book findByTitle(String title);
		
	Book saveNew(Book book);
	
	Book update(Book book);
	
	void delete(Long id);
	
}
