package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.blurdel.sdjpa.domain.Author;

public interface AuthorDao {
	
	List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable);
	
	Author findById(Long id);
	
	Author findByName(String firstName, String lastName);
	
	Author saveNew(Author author);
	
	Author update(Author author);
	
	void delete(Long id);
	
}
