package com.week3.libraryManagement.Library.System.controllers;

import com.week3.libraryManagement.Library.System.dto.AuthorDTO;
import com.week3.libraryManagement.Library.System.dto.BookDTO;
import com.week3.libraryManagement.Library.System.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        return service.getAuthorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{authorid}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthorId(@PathVariable("authorid") Long authorId) {
        return ResponseEntity.ok(service.getBooksByAuthorId(authorId));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createNewAuthor(@RequestBody AuthorDTO authorDTO){
        return service.createNewAuthor(authorDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{authorid}/books")
    public ResponseEntity<BookDTO> addBookToAuthor(
            @PathVariable("authorid") Long authorId,
            @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(service.addBookToAuthor(authorId, bookDTO));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO){
        return service.updateAuthor(id, authorDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public boolean deleteAuthor(@PathVariable Long id){
        return service.deleteAuthor(id);
    }

    @DeleteMapping("/{authorid}/books/{bookid}")
    public ResponseEntity<String> removeBookFromAuthor(
            @PathVariable("authorid") Long authorId,
            @PathVariable("bookid") Long bookId) {
        service.removeBookFromAuthor(authorId, bookId);
        return ResponseEntity.ok("Book with ID " + bookId + " removed from author " + authorId);
    }
}
