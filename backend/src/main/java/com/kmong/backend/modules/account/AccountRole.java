package com.kmong.backend.modules.account;

public enum AccountRole {

    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자");

    private final String description;

    AccountRole(String description) {
        this.description = description;
    }
}
