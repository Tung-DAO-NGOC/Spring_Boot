## 1.Thuộc tính `name` trong annotation `@Entity` khác với thuộc tính name trong `@Table` như thế nào? Hãy giải thích rõ cần thì minh hoạ

-   Name trong `@Entity` dùng để thay đổi tên của entity khi được sử dụng trong trong Hibernate Query Language (HQL) hay Java Persistence Query Language (JPQL)

-   Name trong `@Table` dùng để thay đổi tên bảng chứa dữ liệu của entity khi được lưu xuống database

## 2.Để debug câu lệnh SQL mà Hibernate sẽ sinh ra trong quá trình thực thi, cần phải bổ sung lệnh nào vào file application.properties?

-   `spring.jpa.show-sql=true` để hiện câu lệnh, `spring.jpa.properties.hibernate.format_sql=true` để câu lệnh được format dễ đọc

## 3.Khi sử dụng H2, làm thế nào để xem được cơ sở dữ liệu và viết câu truy vấn?

-   Sử dụng h2-console

## 4.Khi viết mô tả một model, những thuộc tính chúng ta không muốn lưu xuống CSDL thì cần đánh dấu bằng annotation nào?

-   `@Transient`

## 5.Annotation `@Column` dùng để bổ sung tính chất cho cột ứng với một thuộc tính. Tham số nào trong `@Column` sẽ đổi lại tên cột nếu muốn khác với tên thuộc tính, tham số nào chỉ định yêu cầu duy nhất, không được trùng lặp dữ liệu, tham số nào buộc trường không được `null`?

-   Tham số `name` (default "") dùng để đổi tên cột khi lưu xuống database. `spring.jpa.hibernate.naming_strategy=org.hibernate.cfg.EJB3NamingStrategy` nếu muốn tên column không bị format

-   Tham số `unique` với giá trị true để field chỉ có giá trị duy nhất

-   Tham số `nullable` với giá trị false để field không chứa giá trị `null`

## 6.Có 2 sự kiện mà JPA có thể bắt được, viết logic bổ sung: Ngay trước khi đối tượng Entity lưu xuống CSDL (ngay trước lệnh INSERT) / Ngay trước khi đối tượng Entity cập nhật xuống CSDL (ngay trước lệnh UPDATE). Vậy 2 annotation này là gì

-   `@PrePersist` để trigger method trước khi JPA thực hiện `INSERT` query, và `@PreUpdate` để trigger method trước khi JPA thực hiện lệnh `UPDATE`, nhưng chỉ khi data thực sự bị thay đổi trong Persistence context

## 7.Tổ hợp các trường thông tin địa chỉ: country, city, county, addressline thường luôn đi cùng nhau và sử dụng lại trong các Entity khác nhau. Nhóm 2 annotation nào dùng để tái sử dụng, nhúng một Entity vào một Entity khác?

-   `@Embeddable ` để đánh dấu entity này có thể nhúng vào entity khác. Và `@Embedded` \ `@EmbeddedID` để thực thi việc nhúng vào entity khác

## 8.JpaRepository là một interface có sẵn trong thư viện JPA, nó cũng cấp các mẫu hàm thuận tiện cho thao tác dữ liệu. Cụ thể JpaRepository kế thừa từ interface nào?

```java
interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>
```

## 9.Hãy viết khai báo một interface repository thao tác với một Entity tên là Post, kiểu dữ liệu trường Identity là long, tuân thủ interface JpaRepository.

```java
interface PostRepository extends JpaRepository<Post, Long>{}
```

## 10.Khi đã chọn một cột là Identity dùng `@Id` để đánh dấu, thì có cần phải dùng xác định unique dùng annotation@Column(unique=true)không?

-   Không cần thiết, nhưng có để cũng không sao

## 11.Khác biệt giữa`@Id` với `@NaturalId` là gì?

-   Thông thường `@NaturalId` dùng cho các trường định danh thực thể trên thực tế mà không cần xử lý logic (như số căn cước, biển số xe). `@Id` dùng để định danh thực thể trên hệ thống cơ sở dữ liệu. `@Id` thường có dạng Int hoặc Long (int trong database), trong khi `@NaturalId` thì không bắt buộc. Hibernate duy trì việc mapping giữa `@NaturalId` và `@Id` (trên thực tế nếu query entity thông qua `@NaturalId`, Hibernate sẽ tìm `@Id` thông qua `@NaturalId` trước, rồi mới dùng `@Id` tìm được tiến hành query entity cần truy vấn)

## 12.Có những cột không phải primary key (`@Id`) hay `@NaturalId`, dữ liệu có thể trùng lặp (unique không đảm bảo true), nhưng cần đánh chỉ mục(index) để tìm kiếm nhanh hơn vậy phải dùng annotation gì? Hãy viết 1 ví dụ sử dụng annotation đó với index cho 1 column và 1 ví dụ với index cho tổ hợp nhiều column. Tham khảo tại (https://www.baeldung.com/jpa-indexes)

-   Sử dụng annotation `@Index` kèm khai báo `indexes` trên annotaion `@Table`

```java
@Table(indexes = @Index(columnList = "firstName"))
public class Student implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
}
```

Có thể khai báo nhiều index trong bảng, index cho một tổ hợp nhiều column :

```java
@Table(indexes = {
  @Index(columnList = "firstName"),
  @Index(name = "fn_index", columnList = "firstName"),
  @Index(name = "mulitIndex1", columnList = "firstName, lastName"),
  @Index(name = "mulitIndex2", columnList = "lastName, firstName"),
  @Index(name = "mulitSortIndex", columnList = "firstName, lastName DESC"),
  @Index(name = "uniqueIndex", columnList = "firstName", unique = true),
  @Index(name = "uniqueMulitIndex", columnList = "firstName, lastName", unique = true)
})
```

## 13.Annotation `@GeneratedValue` dùng để chọn cách tự sinh unique id cho primary key phải là trường kiểu int hoặc long. Nếu trường primary key có kiểu là String, chúng ta không thể dùng `@GeneratedValue` vậy hãy thử liệt kê các cách đảm bảo sinh ra chuỗi có tính duy nhất?

-   Sử dụng UUID generator / sử dụng Set và string generator tự tạo. Tuy nhiên thực tế nên sử dụng primary key dạng int để tăng tốc độ truy cập, nếu cần thiết cần có identity dạng String thì sử dụng `@NaturalId`. Việc sử dụng song song 1 primary key column dạng int và một unique column chứa string unique có rất nhiều lợi ích trong quá trình sử dụng, bảo trì và nâng cấp hệ thống.

## 14.Giả sử có 1 class Employee với các fields sau {id, emailAddress, firstName, lastName}. Hãy viết các method trong interface EmployeeRespository để: 1- Tìm tất cả các Employee theo `emailAddress` và `lastName`; 2 - Tìm tất cả các Employee khác nhau theo `firstName` hoặc `lastName`; 3 - Tìm tất cả các Employee theo `lastName` và sắp xếp thứ tự theo `firstName` tăng dần; 4. Tìm tất cả các Employeetheo `firstName` không phân biệt hoa thường

```java
    interface EmployeeRespository extends JpaRepository<Employee, Long> {
        List<Employee> findByEmailAddressAndLastName();
        List<Employee> findDistinctByLastnameOrFirstname();
        List<Employee> findByLastNameOrderByFirstNameAsc();
        List<Employee> findByFirstNameIgnoreCase();
    }
```

## 15.Hãy nêu cách sử dụng của `@NamedQuery` và `@Query`. Cho ví dụ

-   `@NamedQuery` được sử dụng ở ngay class Entity để khai báo câu query với JPQL, sau đó chỉ cần khai báo lại method trong interface repository.
-   `@Query` để khai báo câu query tại interface repository ở mỗi method, có thể sử cả JPQL lẫn native query.

Ví dụ

```java

@Entity
@NamedQuery(name = "User.findByEmailAddress",
  query = "select u from User u where u.emailAddress = ?1")
public class User {

    // Id....


    private String emailAddress;
}

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.emailAddress like %?1")
    List<User> findByEmailAddressStartWith(String startWith);

    User findByEmailAddress(String emailAddress);
}

```

## 16.Làm thế nào để có thể viết custom method implemetations cho Jpa Repository. Nêu ví dụ:

-   Có nhiều cách để thực hiện, đây chỉ là một trong các ví dụ

```java
// Interface: NewEntityRepository.java

public interface NewEntityRepository<T, ID extends Serializable> extends JpaRepository<T, ID extends Serializable>,  {
    Optional<T> customQueryByIDMethod( ID id );
}

// Class: NewEntityRepositoryImpl.java

public class NewEntityRepositoryImpl <T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements NewEntityRepository<T, ID extends Serializable>{
    private EntityManager entityManager;

    public MyRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Optional<T> customQueryByIDMethod( ID id ){
        // implementation goes here
    }
}
```

## 17.Hãy nêu 1 ví dụ sử dụng sorting và paging khi query đối tượng Employee ở trên

```java

// Entity: Employee
@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    private Long id;
    private String lastName;
    private String firstName;
    private String emailAddress;
}

// Interface
public interface EmployeeRepository extends JpaRepository<Employee, Long>,  {
   List<Product> findAllByFirstName(String firstName, Pageable pageable);
}

// Service
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository empRepo

    Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
    Pageable firstPageWithThreeElementsSortByLastName = PageRequest.of(0, 3, Sort.by("lastName"));

    List<Employee> listEmp = empRepo.findAllByFirstName("first_name", firstPageWithTwoElements);
    Page<Employee> pageEmp = empRepo.findAll(firstPageWithThreeElementsSortByLastName);
}

```
