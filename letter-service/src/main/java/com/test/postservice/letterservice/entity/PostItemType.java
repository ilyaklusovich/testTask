package com.test.postservice.letterservice.entity;

public enum PostItemType {
    LETTER("letter"),
    PACKAGE("package"),
    WRAPPER("wrapper"),
    POSTCARD("postcard");

    private final String name;

    PostItemType(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return name;
    }
}
