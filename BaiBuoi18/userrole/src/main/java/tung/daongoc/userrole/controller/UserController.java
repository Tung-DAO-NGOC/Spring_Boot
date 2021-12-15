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
    private static final String ATT_CREATE_SUCCESS = "create_success";
    private static final String ATT_ERROR_INDEX = "error";

    private static final String SESSION_ATT_UUID = "uuid";

    private static final String PAGE_INDEX = "index";
    private static final String PAGE_CREATE = "create";
    private static final String PAGE_RECOVER_PASS = "recover_password";

    @Autowired
    UserGateway userGateway;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model){
        model.addAttribute(ATT_ERROR_INDEX, "");
        model.addAttribute(ATT_CREATE_SUCCESS, "");
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
        model.addAttribute("recoverEmail", new UserRequestRecover());
        return PAGE_RECOVER_PASS;
    }

    @GetMapping(value = "/basic")
    public String basicGet(Model model, HttpSession httpSession) throws Exception{
        if (httpSession.getAttribute(SESSION_ATT_UUID).toString().isEmpty()) {
            throw new Exception();
        }
        String uuidUser = httpSession.getAttribute(SESSION_ATT_UUID).toString();
        UserResponse userResponse = userGateway.findUserByUuid(uuidUser);
        model.addAttribute("isAdmin", userResponse.getRoleList().contains(Role.ADMIN.getRoleName()));
        model.addAttribute("user_name", userResponse.getFullName());
        model.addAttribute("user_email", userResponse.getEmail());
        model.addAttribute("user_role", userResponse.getRoleList());
        model.addAttribute("user_event", userResponse.getEventList());
        return "basic";
    }

    @GetMapping(value = "/logout")
    public String logoutGet(Model model, HttpSession httpSession){
        httpSession.removeAttribute(SESSION_ATT_UUID);
        return "redirect:/index";
    }

    @PostMapping(value = "/login")
    public String loginPost(@Valid @ModelAttribute(value = ATT_LOGIN_USER) UserRequestLogin loginUser, BindingResult br, Model model, HttpSession httpSession){

        if (br.hasErrors()) {
            model.addAttribute(ATT_LOGIN_USER, loginUser);
            return PAGE_INDEX;
        } else {
            Map<String, Object> returnResult = userGateway.userLogin(loginUser);
            if (returnResult.containsKey("fail")) {
                model.addAttribute(ATT_ERROR_INDEX, returnResult.get("fail").toString());
                model.addAttribute(ATT_LOGIN_USER, returnResult.get("userLogin"));
                return PAGE_INDEX;
            } else {
                httpSession.setAttribute(SESSION_ATT_UUID, returnResult.get("uuid"));
                return "redirect:/basic";
            }
        }
    }

    @PostMapping(value = "/create")
    public String createPost(@Valid @ModelAttribute(value = "createUser") UserRequestCreate createRequest, BindingResult br, Model model){

        if (br.hasErrors()) {
            model.addAttribute("createUser", createRequest);
            return PAGE_CREATE;
        } else {
            model.addAttribute(ATT_ERROR_INDEX, "");
            model.addAttribute(ATT_CREATE_SUCCESS,userGateway.createUser(createRequest));
            model.addAttribute(ATT_LOGIN_USER, new UserRequestLogin());
            return PAGE_INDEX;
        }
    }

    @PostMapping(value = "/recover")
    public String recoverPost(@Valid @ModelAttribute(value = "recoverEmail") UserRequestRecover requestRecover, BindingResult br, Model model){
        if (!br.hasErrors()) {
            Map<String, String> returnResult = userGateway.recoverPassword(requestRecover);
            if (returnResult.containsKey("notFound")) {
                model.addAttribute("noEmail", returnResult.get("notFound"));
            } else {
                model.addAttribute("newPassword", returnResult.get("newPassword"));
            }
        }
        model.addAttribute("recoverEmail", requestRecover);
        return PAGE_RECOVER_PASS;
    }
}
