package tung.daongoc.userrole.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tung.daongoc.userrole.constant.Role;
import tung.daongoc.userrole.controller.gateway.UserGateway;
import tung.daongoc.userrole.exception.notfound.UuidNotFoundException;
import tung.daongoc.userrole.model.request.user.*;
import tung.daongoc.userrole.model.response.UserResponse;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping()
@Slf4j
public class UserController {

    private static final String ATT_LOGIN_USER = "loginUser";
    private static final String ATT_CREATE_SUCCESS = "create_success";
    private static final String ATT_ERROR_INDEX = "error";
    private static final String ATT_CHANGE_PASS = "changePassword";
    private static final String ATT_RECOVER_EMAIL = "recoverEmail";
    private static final String ATT_CHANGE_PASS_RESULT = "result_update_pass";
    private static final String ATT_CHANGE_INFO = "changeInfo";
    private static final String ATT_CHANGE_INFO_RESULT = "result_update_info";

    private static final String SESSION_ATT_UUID = "uuid";

    private static final String PAGE_INDEX = "index";
    private static final String PAGE_CREATE = "create";
    private static final String PAGE_RECOVER_PASS = "recover_password";
    private static final String PAGE_CHANGE_PASS ="change_password";
    private static final String PAGE_CHANGE_INFO = "change_info";

    @Autowired
    @SuppressWarnings("unused")
    UserGateway userGateway;

    @GetMapping(value = {"/", "/login", "/index" })
    public String index(Model model, HttpSession httpSession){
        if (httpSession.getAttribute(SESSION_ATT_UUID) != null){
            if (userGateway.userLoginWithUuid(httpSession.getAttribute(SESSION_ATT_UUID).toString())) {
                return "redirect:/basic";
            }
        }
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
        model.addAttribute(ATT_RECOVER_EMAIL, new UserRequestRecover());
        return PAGE_RECOVER_PASS;
    }

    @GetMapping(value = "/change_info")
    public String changeEmailGet(Model model, HttpSession httpSession){
        if (httpSession.getAttribute(SESSION_ATT_UUID) == null) throw new UuidNotFoundException();
        UserResponse userResponse = userGateway
                .findUserByUuid(httpSession
                .getAttribute(SESSION_ATT_UUID)
                .toString());
        UserRequestUpdateInfo userRequestUpdateInfo = UserRequestUpdateInfo
                .builder()
                    .email(userResponse.getEmail())
                    .fullName(userResponse.getFullName())
                .build();

        model.addAttribute(ATT_CHANGE_INFO, userRequestUpdateInfo);
        model.addAttribute(ATT_CHANGE_INFO_RESULT, false);
        return PAGE_CHANGE_INFO;
    }

    @GetMapping(value = "/change_password")
    public String changePassGet(Model model){
        model.addAttribute(ATT_CHANGE_PASS_RESULT, false);
        model.addAttribute(ATT_CHANGE_PASS, new UserRequestUpdatePassword());
        return PAGE_CHANGE_PASS;
    }

    @GetMapping(value = "/change_role")
    public String changeRoleGet(Model model, HttpSession httpSession){
        // TODO
        return "change_role";
    }

    @GetMapping(value = "/basic")
    public String basicGet(Model model, HttpSession httpSession) throws UuidNotFoundException{
        if (httpSession.getAttribute(SESSION_ATT_UUID) == null) {
            throw new UuidNotFoundException();
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
    public String recoverPost(@Valid @ModelAttribute(value = ATT_RECOVER_EMAIL) UserRequestRecover requestRecover, BindingResult br, Model model){
        if (!br.hasErrors()) {
            Map<String, String> returnResult = userGateway.recoverPassword(requestRecover);
            if (returnResult.containsKey("notFound")) {
                model.addAttribute("noEmail", returnResult.get("notFound"));
            } else {
                model.addAttribute("newPassword", returnResult.get("newPassword"));
            }
        }
        model.addAttribute(ATT_RECOVER_EMAIL, requestRecover);
        return PAGE_RECOVER_PASS;
    }

    @PostMapping(value = "/change_info")
    public String changeEmailPost(@Valid @ModelAttribute(name = ATT_CHANGE_INFO)UserRequestUpdateInfo userRUI, BindingResult br, Model model, HttpSession httpSession){
        if (br.hasErrors()){
            model.addAttribute(ATT_CHANGE_INFO, userRUI);
            model.addAttribute(ATT_CHANGE_INFO_RESULT, false);
        } else {
            if (httpSession.getAttribute(SESSION_ATT_UUID) == null) throw new UuidNotFoundException();
            userRUI.setUuid(httpSession.getAttribute(SESSION_ATT_UUID).toString());
            userGateway.updateInfo(userRUI);
            model.addAttribute(ATT_CHANGE_INFO, userRUI);
            model.addAttribute(ATT_CHANGE_INFO_RESULT, true);
        }
        return PAGE_CHANGE_INFO;
    }

    @PostMapping(value = "/change_password")
    public String changePassPost(@Valid @ModelAttribute(value = ATT_CHANGE_PASS) UserRequestUpdatePassword userRUP, BindingResult br, Model model, HttpSession httpSession){
        if (!userRUP.getPassword().equals(userRUP.getReconfirmPassword())){
            br.addError(new FieldError(ATT_CHANGE_PASS, "reconfirmPassword", "Password not match"));
        }
        if (br.hasErrors()){
            model.addAttribute(ATT_CHANGE_PASS, userRUP);
            model.addAttribute(ATT_CHANGE_PASS_RESULT, false);
        } else {
            if (httpSession.getAttribute(SESSION_ATT_UUID) == null) throw new UuidNotFoundException();
            userRUP.setUuid(httpSession.getAttribute(SESSION_ATT_UUID).toString());
            userGateway.updatePassword(userRUP);
            model.addAttribute(ATT_CHANGE_PASS_RESULT, true);
            model.addAttribute(ATT_CHANGE_PASS, new UserRequestUpdatePassword());
        }

        return PAGE_CHANGE_PASS;
    }

    @PostMapping(value = "/change_role")
    public String changeRolePost(){
        return  null;
    }
}
