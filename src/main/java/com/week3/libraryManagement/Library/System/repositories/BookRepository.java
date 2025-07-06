package com.week3.libraryManagement.Library.System.repositories;

import com.week3.libraryManagement.Library.System.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
