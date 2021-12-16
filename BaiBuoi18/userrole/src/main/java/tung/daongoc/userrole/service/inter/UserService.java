package tung.daongoc.userrole.service.inter;


import tung.daongoc.userrole.model.request.user.*;
import tung.daongoc.userrole.model.response.UserResponse;

import java.util.Map;

@SuppressWarnings("unused")
public interface UserService {

    Map<String, Object> userLoginFail(UserRequestLogin userLogin);

    Map<String, Object> userLoginSuccess(UserRequestLogin userLogin);

    UserResponse userFindByUuid(String uuid);

    @SuppressWarnings("SameReturnValue")
    boolean createUser(UserRequestCreate userRequestCreate);

    Map<String, String> userRecoverFail(UserRequestRecover userRequestRecover);

    Map<String, String> userRecoverSuccess(UserRequestRecover userRequestRecover);

    void updatePassword(UserRequestUpdatePassword userRUP);

    boolean userLoginWithUuid(String uuid);

    void updateInfo(UserRequestUpdateInfo userRUI);

}
