package tung.daongoc.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class thymeController {
    @GetMapping(value = { "/", "/index" })
    public String homePageGet(Model model) {
        model.addAttribute("active", "home");
        return "index";
    }

    @GetMapping("/list")
    public String listPageGet(Model model) {
        model.addAttribute("active", "list");
        return "list";
    }

    @GetMapping("/add")
    public String addPageGet(Model model) {
        model.addAttribute("active", "add");
        return "add";
    }

    @GetMapping("/view")
    public String viewPageGet(Model model) {
        model.addAttribute("active", "view");
        return "view";
    }
}
