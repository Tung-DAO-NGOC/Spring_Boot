package tung.daongoc.userrole.controller.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;

@MessagingGateway
public interface UserGateway {
    @Gateway(requestChannel = GatewayName.User.LOGIN)
    void userLogin(UserRequestLogin userRequestLogin);
}
