package tung.daongoc.userrole.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import tung.daongoc.userrole.service.inter.UserService;

@Slf4j
public abstract class UserServiceImpl implements UserService {
    @Override
    public String testingGateWay(String input) {
        log.info(UserServiceImpl.class.getName());
        return input;
    }
}
