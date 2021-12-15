package tung.daongoc.userrole.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import tung.daongoc.userrole.constant.Event;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.constant.Role;
import tung.daongoc.userrole.model.entity.EventEntity;
import tung.daongoc.userrole.model.entity.RoleEntity;
import tung.daongoc.userrole.model.entity.UserEntity;
import tung.daongoc.userrole.model.request.user.UserRequestCreate;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.model.request.user.UserRequestRecover;
import tung.daongoc.userrole.model.response.EventResponse;
import tung.daongoc.userrole.model.response.UserResponse;
import tung.daongoc.userrole.repository.inter.EventRepo;
import tung.daongoc.userrole.repository.inter.RoleRepo;
import tung.daongoc.userrole.repository.inter.UserRepo;
import tung.daongoc.userrole.service.inter.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    RoleRepo roleRepo;

    private static final String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss";

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
    @SuppressWarnings("java:S3655")
    public Map<String, Object> userLoginSuccess(UserRequestLogin userLogin) {
        log.info("User login success process");
        UserEntity userFound = userRepo.findByEmail(userLogin.getEmail()).get();
        this.addAnEvent(Event.LOGIN, userFound);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuid", userFound.getUuid());
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.GET_USER_UUID)
    @SuppressWarnings("java:S3655")
    public UserResponse userFindByUuid(String uuid) {
        log.info("Get user by uuid");
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        UserEntity userEntity = userRepo.findByUuid(uuid).get();
        UserResponse userResponse = UserResponse.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .build();
        userResponse.setRoleList(userEntity.getRoleList()
                .stream()
                .map(RoleEntity::getRoleName)
                .toList());
        userResponse.setEventList(userEntity.getEventList()
                .stream()
                .map(p -> (EventResponse.builder()
                        .eventName(p.getEvent().getEventName())
                        .createdDate(dateFormat.format(p.getCreatedDate()))
                        .build()))
                .toList());

        return userResponse;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.CREATE_USER)
    public boolean createUser(UserRequestCreate userRequestCreate) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userRequestCreate.getEmail());
        newUser.setFullName(userRequestCreate.getFullName());
        newUser.setPassword(userRequestCreate.getPassword());
        newUser.setUuid("");
        newUser.setRoleList(new ArrayList<>());
        newUser.setEventList(new ArrayList<>());
        this.addARole(Role.DEVELOPER, newUser);
        this.addAnEvent(Event.CREATE, newUser);

        return true;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.RECOVER_FAILED)
    public Map<String, String> userRecoverFail(UserRequestRecover userRequestRecover) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("notFound", "No email is found!");
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.RECOVER_GENERATE_PASSWORD)
    @SuppressWarnings("java:S3655")
    public Map<String, String> userRecoverSuccess(UserRequestRecover userRequestRecover) {
        Map<String, String> returnMap = new HashMap<>();
        String newPassword = RandomStringUtils.random(10, true, false);
        UserEntity existedUser = userRepo.findByEmail(userRequestRecover.getEmail()).get();
        existedUser.setPassword(newPassword);
        addAnEvent(Event.RECOVER, existedUser);
        userRepo.save(existedUser);
        returnMap.put("newPassword", newPassword);
        return returnMap;
    }

    private void addAnEvent(Event event, UserEntity userEntity){
        EventEntity newEvent = new EventEntity();
        newEvent.setEvent(event);
        newEvent.setCreatedDate(Date.from(Instant.now()));
        userEntity.addEvent(newEvent);
        eventRepo.save(newEvent);
        userRepo.save(userEntity);
    }

    private void addARole(Role role, UserEntity userEntity){
        Optional<RoleEntity> roleCheck = roleRepo.findByRoleName(role.getRoleName());
        if (roleCheck.isPresent()) {
            RoleEntity existedRole = roleCheck.get();
            userEntity.addRole(existedRole);
            roleRepo.save(existedRole);
            userRepo.save(userEntity);
        } else {
            RoleEntity newRole = new RoleEntity();
            newRole.setRole(role);
            userEntity.addRole(newRole);
            roleRepo.save(newRole);
            userRepo.save(userEntity);
        }
    }

}
