package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.blurdel.sdjpa.domain.Book;

public class BookDaoJdbcTemplate implements BookDao {

private final JdbcTemplate template;
	
	
	public BookDaoJdbcTemplate(JdbcTemplate template) {
		this.template = template;
	}

	
	@Override
	public List<Book> findAllBooksSortedByTitle(Pageable pageable) {
		String sql = "select * from book order by title " + 
				pageable.getSort().getOrderFor("title").getDirection().name()
				+ " limit ? offset ?";
		
		System.out.println(sql);
		
		return template.query(sql, getRowMapper(), pageable.getPageSize(), pageable.getOffset());
	}
	
	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		return template.query("select * from book limit ? offset ?", getRowMapper(), 
				pageable.getPageSize(), pageable.getOffset());
	}
	
	@Override
	public List<Book> findAllBooks(int pageSize, int offset) {
		return template.query("select * from book limit ? offset ?", getRowMapper(), pageSize, offset);
	}
	
	@Override
	public List<Book> findAllBooks() {
		return template.query("select * from book", getRowMapper());
	}
	
	@Override
	public Book getById(Long id) {
		return template.queryForObject("select * from book where id=?", getRowMapper(), id);
	}

	@Override
	public Book findByTitle(String title) {
		return template.queryForObject("select * from book where title=?",getRowMapper(), title);
	}

	@Override
	public Book saveNew(Book book) {
		template.update("insert into book (isbn, publisher, title, author_id) values (?,?,?,?)",
				book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());
		
		Long lastId = template.queryForObject("select LAST_INSERT_ID()", Long.class);
		
		return this.getById(lastId);
	}

	@Override
	public Book update(Book book) {
		template.update("update book set isbn=?, publisher=?, title=?, author_id=? where id=?",
				book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId(), book.getId());
		
		return this.getById(book.getId());
	}

	@Override
	public void delete(Long id) {
		template.update("delete from book where id=?", id);
	}

	private RowMapper<Book> getRowMapper() {
		return new BookMapper();
	}
	
}
