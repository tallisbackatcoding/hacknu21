package com.example.eventfinder.exception;

public enum ErrorCode {
    WRONG_CREDENTIALS_ERROR("Incorrect username or password"),
    USER_EXISTS_ERROR("Such username already exists");

    private final String resourceKey;

    ErrorCode(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public String getMessage(Object... objects) {
        return MessageSourceHelper.getMessage(getResourceKey(), LanguageUtils.getLocale(), objects);
    }
}
