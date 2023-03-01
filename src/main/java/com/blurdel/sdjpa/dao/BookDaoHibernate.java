package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.blurdel.sdjpa.domain.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class BookDaoHibernate implements BookDao {

	private final EntityManagerFactory emf;
	
	
	public BookDaoHibernate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	
	@Override
	public List<Book> findAllBooksSortedByTitle(Pageable pageable) {
		return null;
	}

	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
			query.setFirstResult(Math.toIntExact(pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			return query.getResultList();
		} 
		finally {
			em.close();
		}
	}

	@Override
	public List<Book> findAllBooks(int pageSize, int offset) {
		return null;
	}

	@Override
	public List<Book> findAllBooks() {
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
			return query.getResultList();
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public Book getById(Long id) {
		EntityManager em = getEntityManager();
		Book book = em.find(Book.class, id);
		em.close();
		return book;
	}

	@Override
	public Book findByTitle(String title) {
		EntityManager em = getEntityManager();
		
		try {
			// Note: Native query table names must match database
			Query query = em.createNativeQuery("select * from book where title = :title", Book.class);
			
			query.setParameter("title", title);
			
			return (Book) query.getSingleResult();
		} 
		finally {
			em.close();
		}
	}

	@Override
	public Book saveNew(Book book) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.persist(book);
		em.flush();
		em.getTransaction().commit();
		em.close();
		
		return book;
	}

	@Override
	public Book update(Book book) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.merge(book);
		em.flush();
		em.clear();
		
		Book updated = em.find(Book.class, book.getId());
		
		em.getTransaction().commit();
		em.close();
		return updated;
	}

	@Override
	public void delete(Long id) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Book book = em.find(Book.class, id);
		em.remove(book);
		em.flush();
		em.getTransaction().commit();
		em.close();
	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
