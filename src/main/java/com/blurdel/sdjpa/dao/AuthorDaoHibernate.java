package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.blurdel.sdjpa.domain.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;


public class AuthorDaoHibernate implements AuthorDao {

	private final EntityManagerFactory emf;
	
	
	public AuthorDaoHibernate(EntityManagerFactory emf) {
		this.emf = emf;
	}

	
	@Override
	public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
		EntityManager em = getEntityManager();
		
		try {
			String hql = "select a from Author a where a.lastName = :lastName ";
			
			if (pageable.getSort().getOrderFor("firstname") != null) {
				hql += " order by a.firstName " + pageable.getSort().getOrderFor("firstname")
						.getDirection().name();
			}
			
			TypedQuery<Author> query = em.createQuery(hql, Author.class);
			
			query.setParameter("lastName", lastName);
			query.setFirstResult(Math.toIntExact(pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			
			return query.getResultList();
		} 
		finally {
			em.close();
		}
	}

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
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
