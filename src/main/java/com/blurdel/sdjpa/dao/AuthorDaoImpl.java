package com.blurdel.sdjpa.dao;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Author;
import com.blurdel.sdjpa.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final AuthorRepository authorRepo;
	
	
	public AuthorDaoImpl(AuthorRepository authorRepository) {
		this.authorRepo = authorRepository;
	}

	@Override
	public Author findById(Long id) {
		return authorRepo.getById(id);
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		return authorRepo.findAuthorByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Author saveNew(Author author) {
		return authorRepo.save(author);
	}

	@Transactional
	@Override
	public Author update(Author author) {
		Author fetched = authorRepo.getById(author.getId());
		fetched.setFirstName(author.getFirstName());
		fetched.setLastName(author.getLastName());
		return authorRepo.save(fetched);
	}

	@Override
	public void delete(Long id) {
		authorRepo.deleteById(id);
	}
	
}
