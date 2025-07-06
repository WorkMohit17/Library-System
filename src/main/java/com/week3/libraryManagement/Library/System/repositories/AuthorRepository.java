package com.week3.libraryManagement.Library.System.repositories;

import com.week3.libraryManagement.Library.System.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

}
