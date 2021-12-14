package tung.daongoc.userrole.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.entity.UserEntity;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.model.response.EventResponse;
import tung.daongoc.userrole.model.response.UserResponse;
import tung.daongoc.userrole.repository.inter.UserRepo;
import tung.daongoc.userrole.service.inter.UserService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.LOGIN_FAILED)
    public Map<String, Object> userLoginFail(UserRequestLogin userLogin) {
        log.info("User login fail process");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("userLogin", userLogin);
        returnMap.put("fail", "Wrong email or password");
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.LOGIN_SUCCESS)
    public Map<String, Object> userLoginSuccess(UserRequestLogin userLogin) {
        log.info("User login success process");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuid", userRepo.findByEmail(userLogin.getEmail()).get().getUuid());
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.GET_USER_UUID)
    public UserResponse userFindByUuid(String uuid) {
        UserEntity userEntity = userRepo.findByUuid(uuid).get();
        UserResponse userResponse = UserResponse.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .build();
        userResponse.setRoleList(userEntity.getRoleList()
                .stream()
                .map(p -> p.getRole())
                .toList());
        userResponse.setEventList(userEntity.getEventList()
                .stream()
                .map(p -> (EventResponse.builder()
                                .event(p.getEvent())
                                .createdDate(p.getCreatedDate())
                                .build()))
                .toList());
        return userResponse;
    }
}
