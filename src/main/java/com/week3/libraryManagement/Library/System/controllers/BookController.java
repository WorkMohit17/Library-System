package com.week3.libraryManagement.Library.System.controllers;

import com.week3.libraryManagement.Library.System.dto.AuthorDTO;
import com.week3.libraryManagement.Library.System.dto.BookDTO;
import com.week3.libraryManagement.Library.System.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return service.getBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{bookId}/author")
    public ResponseEntity<AuthorDTO> getAuthorByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(service.getAuthorByBookId(bookId));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createNewBook(@RequestBody BookDTO BookDTO){
        return service.createNewBook(BookDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO BookDTO){
        return service.updateBook(id, BookDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{bookId}/change-author")
    public ResponseEntity<BookDTO> changeAuthorOfBook(
            @PathVariable Long bookId,
            @RequestParam Long newAuthorId) {
        return ResponseEntity.ok(service.changeAuthorOfBook(bookId, newAuthorId));
    }


    @DeleteMapping(path = "/{id}")
    public boolean deleteBook(@PathVariable Long id){
        return service.deleteBook(id);
    }
    
}
