package tung.daongoc.userrole.service.inter;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.request.user.UserRequestCreate;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.model.request.user.UserRequestRecover;
import tung.daongoc.userrole.model.response.UserResponse;

import java.util.Map;

public interface UserService {

    Map<String, Object> userLoginFail(UserRequestLogin userLogin);

    Map<String, Object> userLoginSuccess(UserRequestLogin userLogin);

    UserResponse userFindByUuid(String uuid);

    boolean createUser(UserRequestCreate userRequestCreate);

    Map<String, String> userRecoverFail(UserRequestRecover userRequestRecover);

    Map<String, String> userRecoverSuccess(UserRequestRecover userRequestRecover);


}
