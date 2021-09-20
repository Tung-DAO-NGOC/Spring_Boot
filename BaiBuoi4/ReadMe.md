# Thymeleaf render

## Bước 1: Tạo POJO Book

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
}

```

## Bước 2: Khai báo repository BookDAO implement DAO interface

```java
@Component
public class BookDAO implements DAO<Book> {

    protected List<Book> listBook = new ArrayList<Book>();
    private String filePath = "classpath:static/listBook.json";

    public BookDAO() {
        try {
            File file = ResourceUtils.getFile(filePath);
            ObjectMapper mapper = new ObjectMapper();
            listBook.addAll(mapper.readValue(file, new TypeReference<List<Book>>(){}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> readAll() {
        return listBook;
    }
```

## Bước 3: Tạo controller để render kết quả

```java
@Controller
@RequestMapping("/")
public class ThymeController {
    @Autowired
    private DAO<Book> bookDAO;

    @GetMapping(value = "book-list")
    public String getBookList(Model model) {
        List<Book> bookList = bookDAO.readAll();
        model.addAttribute("bookList", bookList);
        return "bookData";
    }
}
```

## Bước 4: tạo template Html

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Book List</title>
  </head>
  <body>
    <table class="table">
      <thead class="thead">
        <tr>
          <th scope="col">No.</th>
          <th scope="col">Title</th>
          <th scope="col">Author</th>
        </tr>
      </thead>
      <tbody class="tbody">
        <tr th:each="bookItem, iStat:${bookList}">
          <th scope="row" th:text="${iStat.count}">No.</th>
          <th class="title-content" th:text="*{bookItem.title}">Title</th>
          <th class="author-content" th:text="*{bookItem.author}">Author</th>
        </tr>
      </tbody>
    </table>
  </body>
</html>
```

## Bước 5: chạy file application và kiểm tra kết quả

- Địa chỉ trả kết quả: `http://localhost:8080/book-list`

![ketqua
](img/ketqua.png)
