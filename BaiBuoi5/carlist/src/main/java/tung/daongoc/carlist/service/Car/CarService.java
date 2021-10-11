package tung.daongoc.carlist.service.Car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tung.daongoc.carlist.entity.Car;
import tung.daongoc.carlist.repository.DAO;
import tung.daongoc.carlist.service.iService;

@Component
public class CarService implements iService<Car> {
    @Autowired
    private DAO<Car> carDAO;

    @Override
    public List<Car> getAll() {
        return carDAO.getAll();
    }

    @Override
    public Car getByID(int ID) {
        return carDAO.getByID(ID);
    }

    @Override
    public void updateByID(Car newObject, int ID) {
        carDAO.updateByID(newObject, ID);
    }

    @Override
    public void deleteByID(int ID) {
        carDAO.deleteByID(ID);
    }

    @Override
    public List<Car> search(String keyword, String sort) {
        return carDAO.search(keyword, sort);
    }

}
