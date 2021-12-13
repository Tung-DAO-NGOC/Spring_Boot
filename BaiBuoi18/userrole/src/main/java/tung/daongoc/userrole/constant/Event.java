package tung.daongoc.userrole.constant;

import tung.daongoc.userrole.exception.constant.EventNotFoundException;

import java.util.stream.Stream;

public enum Event {
    CREATE("create","Create Account"),
    LOGIN("login", "Login"),
    UPDATENAMEEMAIL("update_name_email", "Email/Name Updated"),
    UPDATEPASS("update_password", "Password Update"),
    RETRIEVE("retrieve", "Retrieve Password");

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

    public Event of(String eventType){
        return Stream.of(Event.values())
                .filter(p -> p.getEvent().equalsIgnoreCase(eventType))
                .findFirst()
                .orElseThrow(EventNotFoundException::new);
    }

}
