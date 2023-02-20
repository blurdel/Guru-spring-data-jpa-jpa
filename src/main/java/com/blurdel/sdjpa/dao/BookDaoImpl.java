package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Book;
import com.blurdel.sdjpa.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Component
public class BookDaoImpl implements BookDao {

	private final BookRepository bookRepo;	

	
	public BookDaoImpl(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}

	@Override
	public Book getById(Long id) {
		return bookRepo.getById(id);
	}

	@Override
	public Book findByTitle(String title) {
		return bookRepo.findBookByTitle(title)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Book saveNew(Book book) {
		return bookRepo.save(book);
	}

	@Transactional
	@Override
	public Book update(Book book) {
		Book fetched = bookRepo.getById(book.getId());
		fetched.setIsbn(book.getIsbn());
		fetched.setTitle(book.getTitle());
		fetched.setPublisher(book.getPublisher());
		fetched.setAuthorId(book.getAuthorId());
		return bookRepo.save(fetched);
	}

	@Override
	public void delete(Long id) {
		bookRepo.deleteById(id);
	}

}
