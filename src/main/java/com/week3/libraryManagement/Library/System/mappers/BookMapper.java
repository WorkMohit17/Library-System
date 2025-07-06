package com.week3.libraryManagement.Library.System.mappers;

import com.week3.libraryManagement.Library.System.dto.BookDTO;
import com.week3.libraryManagement.Library.System.entities.BookEntity;
import com.week3.libraryManagement.Library.System.entities.AuthorEntity;

public class BookMapper {

    public static BookDTO toDTO(BookEntity entity) {
        return new BookDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getPublishDate(),
                entity.getAuthor().getId()
        );
    }

    public static BookEntity toEntity(BookDTO dto, AuthorEntity author) {
        return new BookEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getPublishDate(),
                author
        );
    }
}
