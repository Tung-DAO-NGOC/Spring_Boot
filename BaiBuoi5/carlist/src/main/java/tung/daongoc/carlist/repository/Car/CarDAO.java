package tung.daongoc.carlist.repository.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tung.daongoc.carlist.entity.Car;
import tung.daongoc.carlist.repository.DAO;

public class CarDAO implements DAO<Car> {
    protected List<Car> listCar = new ArrayList<Car>();

    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    @Override
    public List<Car> getAll() {
        return listCar;
    }

    @Override
    public Car getByID(int ID) {
        return listCar.stream().filter(p -> p.getId() == ID).findFirst().get();
    }

    @Override
    public void updateByID(Car newObject, int ID) {
        listCar = listCar.stream().map(p -> p.getId() == ID ? newObject : p).collect(Collectors.toList());
    }

    @Override
    public void deleteByID(int ID) {
        listCar.remove(this.getByID(ID));
    }

    @Override
    public List<Car> search(String keyword, String sort) {
        List<Car> returnList = listCar.stream().filter(
                p -> containsIgnoreCase(p.getManufacturer(), keyword) || containsIgnoreCase(p.getModel(), keyword))
                .collect(Collectors.toList());
        switch (sort) {
            case "priceInc":
                returnList.sort((c1, c2) -> (c1.getPrice() - c2.getPrice()));
                break;
            case "priceDec":
                returnList.sort((c1, c2) -> (c2.getPrice() - c1.getPrice()));
                break;
            case "saleInc":
                returnList.sort((c1, c2) -> (c1.getSale() - c2.getSale()));
                break;
            case "saleDec":
                returnList.sort((c1, c2) -> (c2.getSale() - c1.getSale()));
                break;
            case "modelInc":
                returnList.sort((c1, c2) -> (c1.getModel().compareToIgnoreCase(c2.getModel())));
                break;
            case "modelDec":
                returnList.sort((c1, c2) -> -(c1.getModel().compareToIgnoreCase(c2.getModel())));
                break;
            case "manuInc":
                returnList.sort((c1, c2) -> (c1.getManufacturer().compareToIgnoreCase(c2.getManufacturer())));
                break;
            case "manuDec":
                returnList.sort((c1, c2) -> -(c1.getManufacturer().compareToIgnoreCase(c2.getManufacturer())));
                break;
            default:
                break;
        }
        return returnList;
    }

}
