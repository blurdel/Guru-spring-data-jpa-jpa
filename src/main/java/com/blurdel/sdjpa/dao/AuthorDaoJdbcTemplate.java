package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.blurdel.sdjpa.domain.Author;

public class AuthorDaoJdbcTemplate implements AuthorDao {

	private final JdbcTemplate template;

	
	public AuthorDaoJdbcTemplate(JdbcTemplate template) {
		this.template = template;
	}

	
	@Override
	public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select * from author where last_name = ? ");
		
		if (pageable.getSort().getOrderFor("firstname") != null) {
			sb.append("order by first_name ").append(pageable.getSort()
					.getOrderFor("firstname").getDirection().name());
		}
		
		sb.append(" limit ? offset ?");		

		return template.query(sb.toString(), getRowMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
	}
	
	@Override
	public Author findById(Long id) {
//		return template.queryForObject("select * from author where id=?", getRowMapper(), id);
		return null;
	}

	@Override
	public Author findByName(String firstName, String lastName) {
//		return template.queryForObject("select id as author_id, first_name, last_name, NULL as isbn from author where first_name=? and last_name=?", 
//				getRowMapper(), firstName, lastName);
		return null;
	}

	@Override
	public Author saveNew(Author author) {
//		template.update("insert into author (first_name, last_name) values (?,?)",
//				author.getFirstName(), author.getLastName());
//		
//		Long lastId = template.queryForObject("select LAST_INSERT_ID()", Long.class);
//
//		return this.findById(lastId);
		return null;
	}

	@Override
	public Author update(Author author) {
//		template.update("update author set first_name=?, last_name=? where id=?",
//				author.getFirstName(), author.getLastName(), author.getId());
//		
//		return this.findById(author.getId());
		return null;
	}

	@Override
	public void delete(Long id) {
//		template.update("delete from author where id=?", id);
	}

	private AuthorMapper getRowMapper() {
		return new AuthorMapper();
	}
	
}
