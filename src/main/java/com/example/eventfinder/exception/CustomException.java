package com.example.eventfinder.exception;

public class CustomException extends Exception {

    private ErrorCode errorCode;
    final transient Object[] objects;
    public CustomException(ErrorCode errorCode, Object... objects) {

        super(errorCode.getMessage(objects));
        this.errorCode = errorCode;
        this.objects = objects;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getObjects() {
        return objects;
    }
}