package tung.daongoc.userrole.controller.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.request.user.*;
import tung.daongoc.userrole.model.response.UserResponse;

import java.util.Map;

@MessagingGateway
@SuppressWarnings("unused")
public interface UserGateway {
    @Gateway(requestChannel = GatewayName.User.LOGIN)
    Map<String, Object> userLogin(UserRequestLogin userRequestLogin);

    @Gateway(requestChannel = GatewayName.User.LOGIN_UUID)
    boolean userLoginWithUuid(String uuid);

    @Gateway(requestChannel = GatewayName.User.GET_USER_UUID)
    UserResponse findUserByUuid(String userUuid);

    @Gateway(requestChannel = GatewayName.User.UPDATE_ROLE_GET_EMAIL)
    Map<String, Object> roleUpdateFindEmail(UserRequestEmail userRequestEmail);

    @Gateway(requestChannel = GatewayName.User.CREATE_USER)
    boolean createUser(UserRequestCreate userRequestCreate);

    @Gateway(requestChannel = GatewayName.User.RECOVER_PASSWORD)
    Map<String, String> recoverPassword(UserRequestEmail userRequestEmail);

    @Gateway(requestChannel = GatewayName.User.UPDATE_PASSWORD)
    void updatePassword(UserRequestUpdatePassword userRUP);

    @Gateway(requestChannel = GatewayName.User.UPDATE_INFO)
    void updateInfo(UserRequestUpdateInfo userRUI);

    @Gateway(requestChannel = GatewayName.User.UPDATE_ROLE)
    void updateRole(UserRequestRole userRequestRole);
}
