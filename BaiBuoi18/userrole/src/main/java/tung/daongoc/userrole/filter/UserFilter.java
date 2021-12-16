package tung.daongoc.userrole.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Filter;
import org.springframework.stereotype.Component;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.entity.UserEntity;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.model.request.user.UserRequestRecover;
import tung.daongoc.userrole.repository.inter.UserRepo;

import java.util.Optional;

@Component
@Slf4j
public class UserFilter {
    @SuppressWarnings("unused")
    @Autowired
    UserRepo userRepo;

    @Filter(inputChannel = GatewayName.User.LOGIN, outputChannel = GatewayName.User.LOGIN_SUCCESS, discardChannel = GatewayName.User.LOGIN_FAILED)
    public boolean loginFilter(UserRequestLogin loginUser){
        log.info("Filter - Email");
        Optional<UserEntity> existedUser = userRepo.findByEmail(loginUser.getEmail());
        if (existedUser.isEmpty()){
            return false;
        } else {
            return existedUser.get().getPassword().equals(loginUser.getPassword());
        }
    }

    @Filter(inputChannel = GatewayName.User.RECOVER_PASSWORD, outputChannel = GatewayName.User.RECOVER_GENERATE_PASSWORD, discardChannel = GatewayName.User.RECOVER_FAILED)
    public boolean recoverPasswordFilter(UserRequestRecover userRequestRecover){
        log.info("Filter - Recover Password");
        Optional<UserEntity> existedUser = userRepo.findByEmail(userRequestRecover.getEmail());
        return existedUser.isPresent();
    }
}
