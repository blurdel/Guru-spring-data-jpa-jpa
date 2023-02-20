package com.blurdel.sdjpa.dao;

import java.util.List;

import com.blurdel.sdjpa.domain.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class BookDaoImpl implements BookDao {

	@Override
	public Book findById(Long id) {
		return null;
	}

	@Override
	public Book findByTitle(String title) {
		return null;
	}

	@Override
	public Book saveNew(Book book) {
		return null;
	}

	@Override
	public Book update(Book book) {
		return null;
	}

	@Override
	public void delete(Long id) {
		
	}

}
