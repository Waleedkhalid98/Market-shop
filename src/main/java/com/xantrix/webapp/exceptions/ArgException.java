package com.xantrix.webapp.exceptions;

public class ArgException extends Exception{


    String message ;

    public ArgException()
    {
        super();
    }

    public ArgException(String message)
    {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
