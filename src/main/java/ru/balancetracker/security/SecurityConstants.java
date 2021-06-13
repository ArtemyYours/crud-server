package ru.balancetracker.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface SecurityConstants {
    public static final String HAS_ROLE_USER = "hasRole('user')";

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize(HAS_ROLE_USER)
    @interface PreAuthorizeUserRole {
    }
}


