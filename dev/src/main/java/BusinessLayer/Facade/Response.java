package main.java.BusinessLayer.Facade;

public class Response<T> {
    private T value;
    private boolean wasException=false;
    private String message;

    public Response(Exception exception){
        wasException=true;
        message=exception.getMessage();
    }

    public Response(T value){
        this.value=value;
    }

    public T getValue() {
        return value;
    }

    public boolean WasException() {
        return wasException;
    }

    public String getMessage() {
        return message;
    }
}
