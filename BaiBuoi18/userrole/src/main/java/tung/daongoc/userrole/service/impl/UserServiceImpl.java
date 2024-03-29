package tung.daongoc.userrole.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tung.daongoc.userrole.constant.Event;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.constant.Role;
import tung.daongoc.userrole.exception.notfound.UuidNotFoundException;
import tung.daongoc.userrole.model.entity.EventEntity;
import tung.daongoc.userrole.model.entity.RoleEntity;
import tung.daongoc.userrole.model.entity.UserEntity;
import tung.daongoc.userrole.model.request.user.*;
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
@SuppressWarnings("unused")
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
    @SuppressWarnings({"java:S3655", "OptionalGetWithoutIsPresent"})
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
        Optional<UserEntity> userOp = userRepo.findByUuid(uuid);
        if (userOp.isEmpty()){
            throw new UuidNotFoundException();
        }
        UserEntity userEntity = userOp.get();
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
        newUser.setPassword(BCrypt.hashpw(userRequestCreate.getPassword(), BCrypt.gensalt(16)));
        newUser.setUuid("");
        newUser.setRoleList(new ArrayList<>());
        newUser.setEventList(new ArrayList<>());
        this.addARole(Role.DEVELOPER, newUser);
        this.addAnEvent(Event.CREATE, newUser);

        return true;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.RECOVER_PASSWORD_FAILED)
    public Map<String, String> userRecoverFail(UserRequestEmail userRequestEmail) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("notFound", "No email is found!");
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.RECOVER_PASSWORD_SUCCESS)
    @SuppressWarnings({"java:S3655", "OptionalGetWithoutIsPresent"})
    public Map<String, String> userRecoverSuccess(UserRequestEmail userRequestEmail) {
        Map<String, String> returnMap = new HashMap<>();
        String newPassword = RandomStringUtils.random(10, true, false);
        UserEntity existedUser = userRepo.findByEmail(userRequestEmail.getEmail()).get();
        existedUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(16)));
        addAnEvent(Event.RECOVER, existedUser);
        userRepo.save(existedUser);
        returnMap.put("newPassword", newPassword);
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.UPDATE_PASSWORD)
    public void updatePassword(UserRequestUpdatePassword userRUP) {
        Optional<UserEntity> userOp = userRepo.findByUuid(userRUP.getUuid());
        if (userOp.isPresent()){
            UserEntity user = userOp.get();
            user.setPassword(BCrypt.hashpw(userRUP.getPassword(), BCrypt.gensalt(16)));
            userRepo.save(user);
            addAnEvent(Event.UPDATE_PASS, user);
        } else {
            throw new UuidNotFoundException();
        }
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.LOGIN_UUID)
    public boolean userLoginWithUuid(String uuid) {
        Optional<UserEntity> userOp = userRepo.findByUuid(uuid);
        if (userOp.isPresent()){
            UserEntity user = userOp.get();
            addAnEvent(Event.LOGIN, user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.UPDATE_INFO)
    public void updateInfo(UserRequestUpdateInfo userRUI) {
        Optional<UserEntity> userOp = userRepo.findByUuid(userRUI.getUuid());
        if (userOp.isPresent()){
            UserEntity user = userOp.get();
            user.setEmail(userRUI.getEmail());
            user.setFullName(userRUI.getFullName());
            userRepo.save(user);
            addAnEvent(Event.UPDATE_INFO, user);
        } else {
            throw new UuidNotFoundException();
        }
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.UPDATE_ROLE_GET_EMAIL_FAILED)
    public Map<String, Object> userUpdateRoleEmailFailed(UserRequestEmail userRequestEmail) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("notFound", null);
        return returnMap;
    }

    @Override
    @ServiceActivator(inputChannel = GatewayName.User.UPDATE_ROLE_GET_EMAIL_SUCCESS)
    @SuppressWarnings({"java:S3655", "OptionalGetWithoutIsPresent"})
    public Map<String, Object> userUpdateRoleEmailSuccess(UserRequestEmail userRequestEmail) {
        UserEntity user = userRepo.findByEmail(userRequestEmail.getEmail()).get();
        UserRequestRole returnResult = new UserRequestRole();
        returnResult.setUuid(user.getUuid());
        returnResult.setRole(user.getRoleList().stream().
                map(p -> p.getRole().getRoleName()).
                toList());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("userRequestRole", returnResult);
        return returnMap;
    }

    @Override
    @SuppressWarnings({"java:S3655", "OptionalGetWithoutIsPresent"})
    @ServiceActivator(inputChannel = GatewayName.User.UPDATE_ROLE)
    public void updateRole(UserRequestRole userRequestRole) {
        UserEntity user = userRepo.findByUuid(userRequestRole.getUuid()).get();
        user.clearRole();
        for (String roleName: userRequestRole.getRole()) {
            addARole(Role.of(roleName), user);
        }
        addAnEvent(Event.UPDATE_ROLE, user);
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
        } else {
            RoleEntity newRole = new RoleEntity();
            newRole.setRole(role);
            userEntity.addRole(newRole);
            roleRepo.save(newRole);
        }
        userRepo.save(userEntity);
    }

}
