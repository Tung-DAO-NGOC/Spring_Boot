package tung.daongoc.userrole.exception.notfound;

public class RoleNotFoundException extends IllegalArgumentException{
    public RoleNotFoundException(){}
    public RoleNotFoundException (String errorMessage){
        super(errorMessage);
    }

    public RoleNotFoundException (String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
