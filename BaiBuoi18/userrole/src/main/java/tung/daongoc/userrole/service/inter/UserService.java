package tung.daongoc.userrole.service.inter;

import org.springframework.integration.annotation.ServiceActivator;

public interface UserService {

    String testingGateWay();

    @ServiceActivator(inputChannel = "gateway-test-input")
    String testingGateWay(String input);
}
