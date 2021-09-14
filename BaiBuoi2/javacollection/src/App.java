import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
    public static void addList(List<Person> listPerson) {
        listPerson.add(new Person("Huy", "VietNam", 38));
        listPerson.add(new Person("Duong", "VietNam", 26));
        listPerson.add(new Person("Ha", "VietNam", 24));
        listPerson.add(new Person("Rosanna", "Spanish", 31));
        listPerson.add(new Person("Alex", "English", 27));
        listPerson.add(new Person("Thomas", "English", 31));
        listPerson.add(new Person("Micaela", "French", 26));
        listPerson.add(new Person("Oliviero", "Spanish", 23));
        listPerson.add(new Person("Giroud", "French", 28));
        listPerson.add(new Person("Bennie", "American", 15));
        listPerson.add(new Person("Rossie", "American", 18));
        listPerson.add(new Person("Lilla", "American", 22));
    }

    public static void main(String[] args) throws Exception {
        List<Person> listPerson = new ArrayList<Person>();
        addList(listPerson);
        // Lọc tuổi 20 - 30
        System.out.println("Danh sach nguoi tu 20 den 30 tuoi");
        listPerson.stream().filter(p -> (p.getAge() >= 20)).filter(p -> (p.getAge() <= 30))
                .sorted((p1, p2) -> (p1.getAge() - p2.getAge())).forEach(p -> System.out.println(p.toString()));
        // Tinh tuoi trung binh
        double avgAllAge = listPerson.stream().mapToDouble(Person::getAge).average().getAsDouble();
        System.out.printf("Tuoi trung binh cua moi nguoi la: %.2f\n", avgAllAge);
        // Tinh tuoi theo quoc gia
        System.out.println("Tinh tuoi trung binh theo quoc gia");
        Set<String> nationality = new HashSet<String>();
        listPerson.stream().forEach(p -> nationality.add(p.getNationality()));
        nationality.stream().forEach(nat -> {
            double avgAge = listPerson.stream().filter(p -> (p.getNationality().equals(nat)))
                    .mapToDouble(Person::getAge).average().getAsDouble();
            System.out.printf("Tuoi trung binh cua %s la: %.2f \n", nat, avgAge);
        });
    }
}
