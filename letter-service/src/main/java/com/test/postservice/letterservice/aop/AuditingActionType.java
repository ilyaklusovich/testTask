package com.test.postservice.letterservice.aop;

public enum AuditingActionType {
    CREATE, UPDATE, DELETE, CHANGE_STATUS;

    public String getDescription() {
        return name();
    }
}
