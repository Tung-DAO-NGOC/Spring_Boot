package tung.daongoc.userrole.constant;

import tung.daongoc.userrole.exception.notfound.EventNotFoundException;

import java.util.stream.Stream;

public enum Event {
    CREATE("create","Create Account"),
    LOGIN("login", "Login"),
    UPDATE_INFO("update_info", "Info Updated"),
    UPDATE_PASS("update_password", "Password Updated"),
    UPDATE_ROLE("update_role", "Update Role"),
    RECOVER("recover_password", "Recover Password");

    private final String eventType;
    private final String eventName;

    Event(String eventType, String eventName) {
        this.eventType = eventType;
        this.eventName = eventName;
    }

    public String getEventType(){
        return this.eventType;
    }

    public String getEventName(){
        return this.eventName;
    }

    public static Event of(String eventType){
        return Stream.of(Event.values())
                .filter(p -> p.getEventType().equalsIgnoreCase(eventType))
                .findFirst()
                .orElseThrow(EventNotFoundException::new);
    }

}
