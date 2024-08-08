package org.example.homework2.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.homework2.model.Book;
import org.example.homework2.model.request.BookRequest;
import org.example.homework2.model.respone.APIResponse;
import org.example.homework2.model.respone.RemoveResponse;
import org.example.homework2.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/api/books")
@Data
public class BookController {
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<APIResponse<Book>> insertBook(@RequestBody BookRequest bookRequest){
        APIResponse<Book> response = APIResponse.<Book>builder()
                .message("Insert book successfully!")
                .payload(bookRepository.insertBook(bookRequest))
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Book>>> getAllBooks(){
        APIResponse<List<Book>> response = APIResponse.<List<Book>>builder()
                .message("Get all books successfully!")
                .payload(bookRepository.getAllBooks())
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Book>> getBookById(@PathVariable UUID id){
        APIResponse<Book> response = APIResponse.<Book>builder()
                .message("Get book ["+id+"] successfully!")
                .payload(bookRepository.getBookById(id))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/title")
    public ResponseEntity<APIResponse<List<Book>>> getBookByTitle(@RequestParam String title){
        APIResponse<List<Book>> response = APIResponse.<List<Book>>builder()
                .message("Get book ["+ title +"] found!")
                .payload(bookRepository.getBookByTitle(title))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Book>> updateBook(@PathVariable("id") UUID id, @RequestBody BookRequest bookRequest){
        APIResponse<Book> response = APIResponse.<Book>builder()
                .message("Book ["+id+"] update successfully!")
                .payload(bookRepository.updateBook(id, bookRequest))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RemoveResponse> deleteBook(@PathVariable("id") UUID id){
        bookRepository.deleteBook(id);
        RemoveResponse response = RemoveResponse.builder()
                .message("Book ["+id+"] delete successfully!")
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
