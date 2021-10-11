package tung.daongoc.carlist.repository.Car;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import tung.daongoc.carlist.entity.Car;

@Component("json")
public class CarDAOjson extends CarDAO {

    private final String PATH = "classpath:static/data/carList.json";

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

}
