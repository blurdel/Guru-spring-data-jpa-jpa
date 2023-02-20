package com.blurdel.sdjpa.dao;

import java.util.List;

import com.blurdel.sdjpa.domain.Author;

public interface AuthorDao {
	
	Author findById(Long id);
	
	Author findByName(String firstName, String lastName);
	
	Author saveNew(Author author);
	
	Author update(Author author);
	
	void delete(Long id);
	
}
