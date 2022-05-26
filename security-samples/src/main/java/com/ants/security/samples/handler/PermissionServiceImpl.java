package com.ants.security.samples.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lyy
 * @Deprecated
 * @date 2021/8/17
 */
@Slf4j
@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {
    @Override
    public boolean permission(String permission) {
        return false;
    }
}
