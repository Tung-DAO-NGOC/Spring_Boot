package tung.daongoc.film.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tung.daongoc.film.model.Film;

@Controller
@RequestMapping("/")
public class ServerController {
    @GetMapping("/")
    public String pageIndex() {
        return "index";
    }

    @GetMapping("/error")
    public String pageError() {
        return "error";
    }

    @GetMapping("/films")
    public String pageFilms(Model model) {
        List<Film> filmList = List.of(new Film("Gone with the Wind", "Victor Fleming, David O. Selznick", 1939),
                new Film("Bố Già", "Trấn Thành", 2020), new Film("Parasite", "Bong Joon-ho", 2019),
                new Film("Money Heist", "Alex Pina", 2018));
        model.addAttribute("filmList", filmList);
        return "films";
    }
}
