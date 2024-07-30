package com.hitices.storage.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // todo: 根据用户属性和资源属性决定是否授权
        System.out.println("CustomPermissionEvaluator.hasPermission 1");
        System.out.println(authentication.getName());
        System.out.println(targetDomainObject);
        System.out.println(permission);
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        System.out.println("CustomPermissionEvaluator.hasPermission 2");
        return false;
    }
}
