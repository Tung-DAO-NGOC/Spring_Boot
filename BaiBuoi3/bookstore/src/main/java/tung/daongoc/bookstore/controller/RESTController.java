package tung.daongoc.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tung.daongoc.bookstore.model.Book;
import tung.daongoc.bookstore.repository.DAO;

@RestController
@RequestMapping("/api/books")
public class RESTController {
    @Autowired
    private DAO<Book> bookDAO;

    @GetMapping("")
    public ResponseEntity<List<Book>> getListBook() {
        List<Book> listBook = bookDAO.getall();
        return ResponseEntity.status(HttpStatus.OK).body(listBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Optional<Book> bookOptional = bookDAO.get(id);
        Book book = null;
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
        }
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("")
    public ResponseEntity<List<Book>> createBook(@RequestParam int id, @RequestParam String title,
            @RequestParam String description) {
        Book newBook = new Book(id, title, description);
        bookDAO.add(newBook);
        List<Book> listBook = bookDAO.getall();
        return ResponseEntity.status(HttpStatus.CREATED).body(listBook);
    }

    @PutMapping("")
    public ResponseEntity<List<Book>> updateBook(@RequestParam int id, @RequestParam String title,
            @RequestParam String description) {
        Book newBook = new Book(id, title, description);
        bookDAO.update(newBook);
        List<Book> listBook = bookDAO.getall();
        return ResponseEntity.status(HttpStatus.OK).body(listBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Book>> deleteBook(@PathVariable int id) {
        bookDAO.deleteByID(id);
        List<Book> listBook = bookDAO.getall();
        return ResponseEntity.status(HttpStatus.OK).body(listBook);
    }
}
