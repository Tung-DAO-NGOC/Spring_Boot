package tung.daongoc.userrole.exception;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String errorMessage){
        super(errorMessage);
    }

    public InvalidPasswordException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
