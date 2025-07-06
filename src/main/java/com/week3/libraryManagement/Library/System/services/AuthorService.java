package com.week3.libraryManagement.Library.System.services;

import com.week3.libraryManagement.Library.System.dto.AuthorDTO;
import com.week3.libraryManagement.Library.System.dto.BookDTO;
import com.week3.libraryManagement.Library.System.entities.AuthorEntity;
import com.week3.libraryManagement.Library.System.entities.BookEntity;
import com.week3.libraryManagement.Library.System.mappers.AuthorMapper;
import com.week3.libraryManagement.Library.System.mappers.BookMapper;
import com.week3.libraryManagement.Library.System.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors(){
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        return authorEntities.stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AuthorDTO> getAuthorById(Long id){
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        return authorEntity.map(AuthorMapper::toDTO);
    }

    public Optional<AuthorDTO> createNewAuthor(AuthorDTO authorDTO){
        AuthorEntity authorEntity = AuthorMapper.toEntity(authorDTO);
        AuthorEntity saved = authorRepository.save(authorEntity);
        return Optional.of(AuthorMapper.toDTO(saved));
    }

    public Optional<AuthorDTO> updateAuthor(Long id, AuthorDTO authorDTO) {
        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id: " + id + " does not exist"));

        if (authorDTO.getName() != null)
            author.setName(authorDTO.getName());
        if (authorDTO.getBio() != null)
            author.setBio(authorDTO.getBio());


        AuthorEntity updated = authorRepository.save(author);
        return Optional.of(AuthorMapper.toDTO(updated));
    }


    public boolean deleteAuthor(Long id){
        isExistById(id);
        authorRepository.deleteById(id);
        return true;
    }

    private void isExistById(Long id){
        boolean exist = authorRepository.existsById(id);
        if(!exist)
            throw new EntityNotFoundException("Author with id:"+id+" does not exist");
    }

    public List<BookDTO> getBooksByAuthorId(Long id){
        isExistById(id);
        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id:"+id+" does not exist"));
        if(author.getBooks().isEmpty())
                return new ArrayList<>();
        return author.getBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO addBookToAuthor(Long id, BookDTO bookDTO) {
        isExistById(id);
        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id: " + id + " does not exist"));
        BookEntity newBook = BookMapper.toEntity(bookDTO, author);
        if (author.getBooks() == null) {
            author.setBooks(new ArrayList<>());
        }
        author.getBooks().add(newBook);
        AuthorEntity updatedAuthor = authorRepository.save(author);
        BookEntity savedBook = updatedAuthor.getBooks()
                .stream()
                .filter(book -> book.getTitle().equals(bookDTO.getTitle()))
                .reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException("Book was not saved"));
        return BookMapper.toDTO(savedBook);
    }

    public void removeBookFromAuthor(Long authorId, Long bookId) {
        isExistById(authorId);
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id: " + authorId + " does not exist"));
        List<BookEntity> books = author.getBooks();
        if (books == null || books.isEmpty())
            throw new EntityNotFoundException("Author has no books to remove");
        boolean removed = books.removeIf(book -> book.getId().equals(bookId));
        if (removed) {
            authorRepository.save(author);
        } else
            throw new EntityNotFoundException("Book with id: " + bookId + " not found for this author");
    }

}
