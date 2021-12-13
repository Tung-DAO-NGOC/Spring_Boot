package tung.daongoc.userrole.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @GetMapping("/{string}")
    public String testGateway(@PathVariable(value = "string") String stringName){
        return gateway.gatewayTesting(stringName);
    }
}
