package tung.daongoc.userrole.constant;

public class GatewayName {

    public static class User{
        public static final String LOGIN = "gateway.user.login";
        public static final String LOGIN_UUID = "gateway.user.login_uuid";
        public static final String LOGIN_FAILED = "gateway.user.login_failed";
        public static final String LOGIN_SUCCESS = "gateway.user.login_success";
        public static final String GET_USER_UUID = "gateway.user.get_user_uuid";
        public static final String CREATE_USER = "gateway.user.create_user";
        public static final String RECOVER_PASSWORD = "gateway.user.recover_password";
        public static final String RECOVER_GENERATE_PASSWORD = "gateway.user.recover_generate_password";
        public static final String RECOVER_FAILED = "gateway.user.recover_failed";
        public static final String UPDATE_PASSWORD = "gateway.user.update_password";
        public static final String UPDATE_INFO = "gateway.user.update_info";

        private User() {
        }
    }

    private GatewayName() {
    }
}
