package tung.daongoc.userrole.exception;

public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(String errorMessage){
        super(errorMessage);
    }

    public UnauthorizedAccessException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
