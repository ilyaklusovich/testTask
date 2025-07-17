package com.test.postservice.letterservice.aop;

public enum AuditingEntityType {
    POST_ITEM, POST_OFFICE;

    public String getDescription() {
        return name();
    }
}
