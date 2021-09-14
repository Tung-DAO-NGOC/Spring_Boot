import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int total = 0;
        for (Person person : listPerson) {
            total += person.getAge();
        }
        System.out.printf("Tuoi trung binh cua tat ca moi nguoi la: %.2f\n", ((float) total / listPerson.size()));
        // Tinh tuoi theo quoc gia
        Map<String, List<Person>> nationalityAge = new HashMap<String, List<Person>>();
        for (Person person : listPerson) {
            List<Person> listNation = nationalityAge.getOrDefault(person.getNationality(), new ArrayList<Person>());
            listNation.add(person);
            nationalityAge.put(person.getNationality(), listNation);
        }
        System.out.println("Tinh tuoi trung binh theo quoc gia");
        for (var nation : nationalityAge.entrySet()) {
            List<Person> nationList = nation.getValue();
            int nationAge = 0;
            for (Person person : nationList) {
                nationAge += person.getAge();
            }
            System.out.printf("Tuoi trung binh cua %s la: %.2f \n", nation.getKey(),
                    ((float) nationAge / nationList.size()));
        }

    }
}
