package tung.daongoc.userrole.controller.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.model.response.UserResponse;

import java.util.Map;

@MessagingGateway
public interface UserGateway {
    @Gateway(requestChannel = GatewayName.User.LOGIN)
    Map<String, Object> userLogin(UserRequestLogin userRequestLogin);

    @Gateway(requestChannel = GatewayName.User.GET_USER_UUID)
    UserResponse findUserByUuid(String userUuid);
}
