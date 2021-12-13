package tung.daongoc.userrole.exception.constant;

import tung.daongoc.userrole.constant.Event;

public class EventNotFoundException extends IllegalArgumentException{
    public EventNotFoundException(){};
    public EventNotFoundException(String errorMessage){
        super(errorMessage);
    }

    public EventNotFoundException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
