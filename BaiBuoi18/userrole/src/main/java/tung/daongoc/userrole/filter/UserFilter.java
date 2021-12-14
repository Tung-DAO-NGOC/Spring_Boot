package tung.daongoc.userrole.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Filter;
import org.springframework.stereotype.Component;
import tung.daongoc.userrole.constant.GatewayName;
import tung.daongoc.userrole.model.entity.UserEntity;
import tung.daongoc.userrole.model.request.user.UserRequestLogin;
import tung.daongoc.userrole.repository.inter.UserRepo;

import java.util.Optional;

@Component
public class UserFilter {
    @Autowired
    UserRepo userRepo;

    @Filter(inputChannel = GatewayName.User.LOGIN, outputChannel = GatewayName.User.LOGIN_SUCCESS, discardChannel = GatewayName.User.LOGIN_FAILED)
    public boolean loginFilter(UserRequestLogin loginUser){
        Optional<UserEntity> existedUser = userRepo.findByEmail(loginUser.getEmail());
        if (!existedUser.isPresent()){
            return false;
        } else {
            if (!existedUser.get().getPassword().equals(loginUser.getPassword()))
                return false;
        }
        return true;
    }
}
