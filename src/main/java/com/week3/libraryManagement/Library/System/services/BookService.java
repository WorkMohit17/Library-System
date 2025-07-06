package com.week3.libraryManagement.Library.System.services;

import com.week3.libraryManagement.Library.System.dto.AuthorDTO;
import com.week3.libraryManagement.Library.System.dto.BookDTO;
import com.week3.libraryManagement.Library.System.entities.AuthorEntity;
import com.week3.libraryManagement.Library.System.entities.BookEntity;
import com.week3.libraryManagement.Library.System.mappers.AuthorMapper;
import com.week3.libraryManagement.Library.System.mappers.BookMapper;
import com.week3.libraryManagement.Library.System.repositories.AuthorRepository;
import com.week3.libraryManagement.Library.System.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks(){
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookDTO> getBookById(Long id){
        Optional<BookEntity> BookEntity = bookRepository.findById(id);
        return BookEntity.map(BookMapper::toDTO);
    }

    public Optional<BookDTO> createNewBook(BookDTO bookDTO) {
        AuthorEntity author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + bookDTO.getAuthorId()));

        BookEntity bookEntity = BookMapper.toEntity(bookDTO, author);
        BookEntity saved = bookRepository.save(bookEntity);
        return Optional.of(BookMapper.toDTO(saved));
    }

    public Optional<BookDTO> updateBook(Long id, BookDTO bookDTO) {
        isExistById(id);
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: " + id + " does not exist"));

        book.setTitle(bookDTO.getTitle());
        book.setPublishDate(bookDTO.getPublishDate());

        if (bookDTO.getAuthorId() != null) {
            AuthorEntity author = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new EntityNotFoundException("Author with id: " + bookDTO.getAuthorId() + " not found"));
            book.setAuthor(author);
        }

        BookEntity updated = bookRepository.save(book);
        return Optional.of(BookMapper.toDTO(updated));
    }


    public boolean deleteBook(Long id){
        isExistById(id);
        bookRepository.deleteById(id);
        return true;
    }

    private void isExistById(Long id){
        boolean exist = bookRepository.existsById(id);
        if(!exist)
            throw new EntityNotFoundException("Book with id: " + id + " does not exist");
    }

    public AuthorDTO getAuthorByBookId(Long bookId){
        isExistById(bookId);
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: " + bookId + " does not exist"));
        AuthorEntity author = book.getAuthor();
        return AuthorMapper.toDTO(author);
    }

    public BookDTO changeAuthorOfBook(Long bookId, Long newAuthorId) {
        isExistById(bookId);
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: " + bookId + " does not exist"));
        AuthorEntity newAuthor = authorRepository.findById(newAuthorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id: " + newAuthorId + " does not exist"));
        book.setAuthor(newAuthor);
        BookEntity updatedBook = bookRepository.save(book);
        return BookMapper.toDTO(updatedBook);
    }
}