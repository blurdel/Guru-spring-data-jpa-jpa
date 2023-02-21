package com.blurdel.sdjpa.repository;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import com.blurdel.sdjpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	Optional<Book> findBookByTitle(String title);
	
	Book readByTitle(String title);
	
	@Nullable
	Book getByTitle(@Nullable String title);
	
	Stream<Book> findAllByTitleNotNull();
	
	@Async
	Future<Book> queryByTitle(String title);
	
}
