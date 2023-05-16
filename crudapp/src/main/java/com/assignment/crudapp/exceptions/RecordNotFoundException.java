package com.assignment.crudapp.exceptions;

public class RecordNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;
    public RecordNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
