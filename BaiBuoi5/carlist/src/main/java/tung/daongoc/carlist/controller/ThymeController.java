package tung.daongoc.carlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tung.daongoc.carlist.entity.Car;
import tung.daongoc.carlist.service.iService;

@Controller
public class ThymeController {
    @Autowired
    private iService<Car> carService;

    @GetMapping(value = { "/", "/index", "/home" })
    public String index(Model model) {
        model.addAttribute("title", "Homepage");
        return "index";
    }

    @GetMapping("/list")
    public String carList(Model model) {
        model.addAttribute("title", "Car List");
        model.addAttribute("carList", carService.getAll());
        return "carList";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable(value = "id") int ID, Model model) {
        model.addAttribute("title", "Edit Form");
        model.addAttribute("car", carService.getByID(ID));
        return "carEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateCar(@ModelAttribute(value = "car") Car car) {
        carService.updateByID(car, car.getId());
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCarForm(@PathVariable(value = "id") int ID, Model model) {
        model.addAttribute("title", "Delete Form");
        model.addAttribute("car", carService.getByID(ID));
        return "carDelete";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable(value = "id") int ID) {
        carService.deleteByID(ID);
        return "redirect:/list";
    }

    @GetMapping("/search")
    public String searchCarForm(Model model) {
        model.addAttribute("title", "Search");
        return "carSearch";
    }

    @PostMapping("/search")
    public String searchCar(@ModelAttribute(value = "keyword") String keyword,
            @ModelAttribute(value = "sort") String sort, Model model) {
        model.addAttribute("title", "Search");
        model.addAttribute("carList", carService.search(keyword, sort));
        return "carSearch";
    }
}
