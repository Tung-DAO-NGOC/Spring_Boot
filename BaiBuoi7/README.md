## Đọc dữ liệu CSV

-   Đọc dữ liệu CSV với dependency OpenCSV, sử dụng BeanAnnotation

    -   Với entity

    ```java
    public class Employee {
        @CsvIgnore
        private int id;
        @CsvBindByName(column = "firstName")
        private String firstName;
        @CsvBindByName(column = "lastName")
        private String lastName;
        @CsvIgnore
        private String fullName;
        @CsvBindByName(column = "emailID")
        private String emailID;
        @CsvBindByName(column = "passportNumber")
        private String passportNumber;

        public void provideFullName() {
            this.fullName = String.join(" ", this.firstName, this.lastName);
        }
    }
    ```

    -   Với constructor của Repository EmployeeDAO

    ```java
        EmployeeDAO() {
        try {
            File file = ResourceUtils.getFile(PATH);
            listEmp = new CsvToBeanBuilder<Employee>(new FileReader(file)).withType(Employee.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AtomicInteger count = new AtomicInteger();

        listEmp.stream().forEach(emp -> {
            emp.provideFullName();
            emp.setId(count.incrementAndGet());
        });
    }
    ```

## Tìm kiếm

-   Tạo entity SearchRequest

    ```java
    @Getter
    @Setter
    @NoArgsConstructor
    @Service
    public class SearchRequest {
        private String field;
        private String keyword;
        private String order;
    }
    ```

-   Trên render thymeleaf

    ```html
    <form th:action="@{/search}" method="post" th:object="${searchReq}">
    	<table class="table table-striped w-auto">
    		<tbody>
    			<tr class="text-center">
    				<td colspan="3"><span class="text-capitalize fw-bold">Search by:</span></td>
    			</tr>
    			<tr>
    				<td>
    					<select class="form-control input-mini" th:field="*{field}" required>
    						<option value="">--Select Field--</option>
    						<option value="email">Email</option>
    						<option value="name">Name</option>
    					</select>
    				</td>
    				<td><input type="text" placeholder="Your keyword" th:field="*{keyword}" /></td>
    				<td>
    					<select class="form-control input-mini" th:field="*{order}" required>
    						<option value="">--Select Order--</option>
    						<option value="inc">A -> Z</option>
    						<option value="desc">Z -> A</option>
    					</select>
    				</td>
    			</tr>
    		</tbody>
    	</table>
    	<button type="submit" class="btn btn-sm btn-primary">Submit</button>
    </form>
    ```

-   Trên xử lý của controller

    ```java
    @GetMapping("/search")
    public String empSearch(Model model) {
        model.addAttribute("title", "Searching");
        model.addAttribute("searchReq", new SearchRequest());
        return "empSearch";
    }

    @PostMapping("/search")
    public String empSearchResult(@ModelAttribute(value = "searchReq") SearchRequest search, Model model) {
        model.addAttribute("title", "Searching");
        model.addAttribute("list", employeeDAO.sort(search));
        return "empSearch";
    }
    ```

-   Trong xử lý của phần repository

    ```java
    @Override
    public List<Employee> sort(SearchRequest searchRequest) {
        List<Employee> result = listEmp;
        switch (searchRequest.getField()) {
            case "email":
                result = result.stream().filter(emp -> emp.matchingEmail(searchRequest.getKeyword()))
                        .collect(Collectors.toList());
                switch (searchRequest.getOrder()) {
                    case "inc":
                        result.sort((emp1, emp2) -> emp1.getEmailID().compareToIgnoreCase(emp2.getEmailID()));
                        break;
                    case "desc":
                        result.sort((emp1, emp2) -> -emp1.getEmailID().compareToIgnoreCase(emp2.getEmailID()));
                        break;
                }
                break;
            case "name":
                result = result.stream().filter(emp -> emp.matchingName(searchRequest.getKeyword()))
                        .collect(Collectors.toList());
                switch (searchRequest.getOrder()) {
                    case "inc":
                        result.sort((emp1, emp2) -> emp1.getFullName().compareToIgnoreCase(emp2.getFullName()));
                        break;
                    case "desc":
                        result.sort((emp1, emp2) -> -emp1.getFullName().compareToIgnoreCase(emp2.getFullName()));
                        break;
                }
                break;
        }
        return result;
    }
    ```
