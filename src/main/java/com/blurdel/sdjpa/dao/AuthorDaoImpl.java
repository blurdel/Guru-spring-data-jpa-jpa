package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

	@Override
	public Author findById(Long id) {
		return null;
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		return null;
	}

	@Override
	public Author saveNew(Author author) {
		return null;
	}

	@Override
	public Author update(Author author) {
		return null;
	}

	@Override
	public void delete(Long id) {
		
	}
	
}
