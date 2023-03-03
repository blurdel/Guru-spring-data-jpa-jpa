package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public List<Book> findAllBooksSortedByTitle(Pageable pageable) {
		Page<Book> bookPage = bookRepo.findAll(pageable);
		
		return bookPage.getContent(); // This method was also called with Sorting, nice way to do paging
	}
	
	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		return bookRepo.findAll(pageable).getContent();
	}
	
	@Override
	public List<Book> findAllBooks(int pageSize, int offset) {
		Pageable pageable = PageRequest.ofSize(pageSize);
		
		if (offset > 0) {
			pageable = pageable.withPage(offset / pageSize);
		}
		else {
			pageable = pageable.withPage(0);
		}
		
		return findAllBooks(pageable);
	}
	
	@Override
	public List<Book> findAllBooks() {
		return bookRepo.findAll();
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
