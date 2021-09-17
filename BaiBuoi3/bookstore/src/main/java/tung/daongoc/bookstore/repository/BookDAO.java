package tung.daongoc.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tung.daongoc.bookstore.model.Book;

@Service
public class BookDAO extends DAO<Book> {

    public BookDAO() {
        listObject.add(new Book(1, "Không gia đình", "Chú bé Remy lang thang theo gánh xiếc của bác Vitaly"));
        listObject.add(new Book(2, "Cuốn theo chiều gió", "Nội chiến Hoa kỳ, cuộc tình giữa Red Butler và Ohara"));
        listObject.add(new Book(3, "Dế Mèn phiêu lưu ký", "Tô Hoài"));
    }

    @Override
    public List<Book> getall() {
        return listObject;
    }

    @Override
    public Optional<Book> get(int id) {
        return listObject.stream().filter(p -> (p.getId() == id)).findFirst();
    }

    @Override
    public void add(Book t) {
        listObject.add(t);
    }

    @Override
    public void update(Book t) {
        Optional<Book> matchBook = listObject.stream().filter(p -> (p.getId() == t.getId())).findFirst();
        if (matchBook.isPresent()) {
            Book book = matchBook.get();
            int index = listObject.indexOf(book);
            listObject.set(index, t);
        } else {
            listObject.add(t);
        }
    }

    @Override
    public void deleteByID(int id) {
        Optional<Book> match = listObject.stream().filter(p -> (p.getId() == id)).findFirst();
        if (match.isPresent()) {
            Book book = match.get();
            listObject.remove(book);
        }
    }

    @Override
    public void delete(Book t) {
        listObject.remove(t);
    }

}
