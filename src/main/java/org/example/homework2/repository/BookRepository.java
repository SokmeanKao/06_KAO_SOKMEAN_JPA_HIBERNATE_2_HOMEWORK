package org.example.homework2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.homework2.model.Book;
import org.example.homework2.model.request.BookRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
@Transactional
public class BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public Book insertBook(BookRequest bookRequest) {
        Book b = new Book();
        b.setTitle(bookRequest.getTitle());
        b.setDescription(bookRequest.getDescription());
        b.setAuthor(bookRequest.getAuthor());
        b.setPublicationYear(bookRequest.getPublicationYear());
        entityManager.persist(b);
        return b;
    }

    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT b FROM Books b", Book.class).getResultList();
    }

    public Book getBookById(UUID id) {
        return entityManager.find(Book.class, id);
    }

    public List<Book> getBookByTitle(String title) {
        return entityManager.createQuery("SELECT b FROM Books b WHERE b.title LIKE :title", Book.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();

    }

    public Book updateBook(UUID id, BookRequest bookRequest) {
        Book b = entityManager.find(Book.class, id);
        entityManager.detach(b);
        b.setTitle(bookRequest.getTitle());
        b.setDescription(bookRequest.getDescription());
        b.setAuthor(bookRequest.getAuthor());
        b.setPublicationYear(bookRequest.getPublicationYear());
        entityManager.merge(b);
        return b;
    }

    public void deleteBook(UUID id) {
        Book b = entityManager.find(Book.class, id);
        entityManager.remove(b);
    }
}
