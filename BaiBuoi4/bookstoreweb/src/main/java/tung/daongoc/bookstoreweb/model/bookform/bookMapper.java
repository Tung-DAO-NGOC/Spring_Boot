package tung.daongoc.bookstoreweb.model.bookform;

import java.util.List;

import tung.daongoc.bookstoreweb.model.Book;

public class bookMapper {
    public static Book bookCreatedToBook(bookCreated nBookCreated, List<Book> listBook) {
        Book returnBook = new Book();
        returnBook.setAuthor(nBookCreated.getAuthor());
        returnBook.setTitle(nBookCreated.getTitle());
        returnBook.setId(listBook.size() + 1);
        return returnBook;
    }
}
