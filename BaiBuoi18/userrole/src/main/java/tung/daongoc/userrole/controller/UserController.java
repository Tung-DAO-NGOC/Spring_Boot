package tung.daongoc.userrole.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tung.daongoc.userrole.constant.Role;
import tung.daongoc.userrole.controller.gateway.UserGateway;
import tung.daongoc.userrole.model.request.user.UserRequestCreate;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.model.request.user.UserRequestRecover;
import tung.daongoc.userrole.model.response.UserResponse;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping()
@Slf4j
public class UserController {

    private static final String ATT_LOGIN_USER = "loginUser";

    private static final String PAGE_INDEX = "index";
    private static final String PAGE_CREATE = "create";

    @Autowired
    UserGateway userGateway;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model){
        model.addAttribute("error", "");
        model.addAttribute(ATT_LOGIN_USER, new UserRequestLogin());
        return PAGE_INDEX;
    }

    @GetMapping(value = "/create")
    public String createGet(Model model){
        model.addAttribute("createUser", new UserRequestCreate());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/recover")
    public String recoverGet(Model model){
        model.addAttribute("notFound", "123456");
        model.addAttribute("password", "123456");
        model.addAttribute("recoverEmail", new UserRequestRecover());
        return "recover_password";
    }

    @GetMapping(value = "/basic")
    public String basicGet(Model model, HttpSession httpSession){
        String uuidUser = httpSession.getAttribute("uuid").toString();
        UserResponse userResponse = userGateway.findUserByUuid(uuidUser);
        model.addAttribute("isAdmin", userResponse.getRoleList().contains(Role.ADMIN));

        return "basic";
    }

    @PostMapping(value = "/login")
    public String loginPost(@Valid @ModelAttribute(value = ATT_LOGIN_USER) UserRequestLogin loginUser, BindingResult br, Model model, HttpSession httpSession){

        if (br.hasErrors()) {
            model.addAttribute(ATT_LOGIN_USER, loginUser);
            return PAGE_INDEX;
        } else {
            Map<String, Object> returnResult = userGateway.userLogin(loginUser);
            if (returnResult.containsKey("fail")) {
                model.addAttribute("error", returnResult.get("fail").toString());
                model.addAttribute(ATT_LOGIN_USER, returnResult.get("userLogin"));
                return PAGE_INDEX;
            } else {
                httpSession.setAttribute("uuid", returnResult.get("uuid"));
                return "redirect:/basic";
            }
        }
    }

    @PostMapping(value = "/create")
    public String createPost(@Valid @ModelAttribute(value = "createUser") UserRequestCreate loginUser, BindingResult br, Model model){

        if (br.hasErrors()) {
            model.addAttribute("createUser", loginUser);
            return PAGE_CREATE;
        } else
            return PAGE_INDEX;
    }

    @PostMapping(value = "/recover")
    public String recoverPost(@Valid @ModelAttribute(value = "recoverEmail") UserRequestRecover requestRecover, BindingResult br, Model model){
        if (br.hasErrors()) {
            model.addAttribute("recoverEmail", requestRecover);
            return "recover_password";
        }
        return PAGE_INDEX;
    }
}
