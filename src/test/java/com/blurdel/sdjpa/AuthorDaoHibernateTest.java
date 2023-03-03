package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpa.dao.AuthorDao;
import com.blurdel.sdjpa.dao.AuthorDaoHibernate;
import com.blurdel.sdjpa.domain.Author;

import jakarta.persistence.EntityManagerFactory;

@ActiveProfiles("mysql")
@DataJpaTest
@ComponentScan(basePackages = {"com.blurdel.sdjpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoHibernateTest {

	@Autowired
	EntityManagerFactory emf;
	
	AuthorDao authorDao;
	
	
	@BeforeEach
    void setUp() {
		authorDao = new AuthorDaoHibernate(emf);
	}
	
	@Test
    void findAllAuthorsByLastName() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 10));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
    }

    @Test
    void findAllAuthorsByLastNameSortLastNameDesc() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllAuthorsByLastNameSortLastNameAsc() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    void findAllAuthorsByLastNameAllRecs() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 100));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(40);
    }
    
}
