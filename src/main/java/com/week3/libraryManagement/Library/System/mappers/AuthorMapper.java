package com.week3.libraryManagement.Library.System.mappers;

import com.week3.libraryManagement.Library.System.dto.AuthorDTO;
import com.week3.libraryManagement.Library.System.entities.AuthorEntity;
import com.week3.libraryManagement.Library.System.entities.BookEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static AuthorDTO toDTO(AuthorEntity entity) {
        List<Long> bookIds = entity.getBooks()
                .stream()
                .map(BookEntity::getId)
                .collect(Collectors.toList());

        return new AuthorDTO(
                entity.getId(),
                entity.getName(),
                entity.getBio(),
                bookIds
        );
    }

    public static AuthorEntity toEntity(AuthorDTO dto) {
        AuthorEntity author = new AuthorEntity();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setBio(dto.getBio());

        return author;
    }
}
