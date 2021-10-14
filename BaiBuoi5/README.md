# Bài tập buổi 5

## Car Entity

```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int id;
    private String model;
    private String manufacturer;
    private int price;
    private int sale;
    private String image;
}
```

## Đọc dữ liệu Json

    ```java
    private final String PATH = "classpath:static/data/carList.json";
    protected List<Car> listCar = new ArrayList<Car>();

    public CarDAOjson() {
        try {
            File file = ResourceUtils.getFile(PATH);
            ObjectMapper mapper = new ObjectMapper();
            listCar.addAll(mapper.readValue(file, new TypeReference<List<Car>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ```

## Sử dụng Thymeleaf render danh sách

-   Phần xử lý dữ liệu

    ```java
        @Override
        public List<Car> getAll() {
            return listCar;
        }
    ```

-   Phần controller cho Thymeleaf

    ```java
    @GetMapping("/list")
    public String carList(Model model) {
        model.addAttribute("title", "Car List");
        model.addAttribute("carList", carService.getAll());
        return "carList";
    }
    ```

-   Phần thiết kế html
    ```html
    <table class="table table-striped w-auto">
    	<thead class="bg-success text-white">
    		<tr style="text-align: center">
    			<th>ID</th>
    			<th>Model</th>
    			<th>Manufacturer</th>
    			<th>Price</th>
    			<th>Sale</th>
    			<th>Image</th>
    			<th colspan="2">Action</th>
    		</tr>
    	</thead>
    	<tbody style="text-align: center">
    		<tr th:each=" car : ${carList}">
    			<td th:text="${car.id}"></td>
    			<td th:text="${car.model}"></td>
    			<td th:text="${car.manufacturer}"></td>
    			<td th:text="${car.price}"></td>
    			<td th:text="${car.sale}"></td>
    			<td><img th:src="${car.image}" /></td>
    			<td>
    				<a th:href="@{/edit/{id}(id = ${car.id})}"
    					><button type="button" class="btn-warning">Edit</button>
    				</a>
    			</td>
    			<td>
    				<a th:href="@{/delete/{id}(id = ${car.id})}"
    					><button type="button" class="btn-danger">Delete</button>
    				</a>
    			</td>
    		</tr>
    	</tbody>
    </table>
    ```

## Cập nhật Car
