package tung.daongoc.bookstoreweb.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import tung.daongoc.bookstoreweb.model.Book;
import tung.daongoc.bookstoreweb.model.bookform.bookCreated;
import tung.daongoc.bookstoreweb.model.bookform.bookMapper;

@Component
public class BookDAO implements DAO<Book, bookCreated> {

    protected List<Book> listBook = new ArrayList<Book>();
    // Trong project SpringBoot khởi tạo maven, classpath luôn trỏ đến thưc mực resources
    private String filePath = "classpath:static/listBook.json";

    public BookDAO() {
        try {
            File file = ResourceUtils.getFile(filePath);
            ObjectMapper mapper = new ObjectMapper();
            listBook.addAll(mapper.readValue(file, new TypeReference<List<Book>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile() {
        try {
            File file = ResourceUtils.getFile(filePath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, listBook);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Book> readAll() {
        return listBook;
    }

    @Override
    public Optional<Book> readByID(int id) {
        Optional<Book> result = listBook.stream().filter(p -> (p.getId() == id)).findFirst();
        return result;
    }

    @Override
    public List<Book> readByKeyword(String keyword) {
        List<Book> returnList = listBook.stream().filter(p -> (p.getTitle().toLowerCase().contains(keyword)))
                .collect(Collectors.toList());
        return returnList;
    }

    @Override
    public Book create(bookCreated object) {
        Book createdBook = bookMapper.bookCreatedToBook(object, listBook);
        listBook.add(createdBook);
        writeFile();
        return createdBook;
    }

    @Override
    public List<Book> deleteByID(int id) {
        listBook = listBook.stream().filter(p -> (p.getId() != id)).collect(Collectors.toList());
        writeFile();
        return listBook;
    }

}
