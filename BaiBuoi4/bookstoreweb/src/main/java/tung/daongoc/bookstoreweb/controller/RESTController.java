package tung.daongoc.bookstoreweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tung.daongoc.bookstoreweb.model.Book;
import tung.daongoc.bookstoreweb.model.bookform.bookCreated;
import tung.daongoc.bookstoreweb.repository.DAO;

@RestController
@RequestMapping("/api")
public class RESTController {
    @Autowired
    private DAO<Book, bookCreated> bookDAO;
    //CRUD

    @GetMapping("")
    public ResponseEntity<?> readAll() {
        List<Book> listBook = bookDAO.readAll();
        return ResponseEntity.status(HttpStatus.OK).body(listBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readByID(@PathVariable int id) {
        Optional<Book> result = bookDAO.readByID(id);
        if (result.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(result.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> readByKeyword(@RequestParam(required = false, defaultValue = "") String keyword) {
        List<Book> returnList = bookDAO.readByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(returnList);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody bookCreated nBookCreated) {
        Book nBook = bookDAO.create(nBookCreated);
        return ResponseEntity.status(HttpStatus.OK).body(nBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable int id) {
        List<Book> returnList = bookDAO.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(returnList);
    }

}
