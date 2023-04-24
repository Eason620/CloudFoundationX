package com.cfx.usercenter.security.core;

import com.cfx.usercenter.security.api.UserDetails;

/**
 * @author Eason
 * @date 2023/4/24
 */
public interface UserDetailsChecker {


    /**
     * 检查用户
     *
     * @param check the UserDetails instance whose status should be checked.
     * @throws com.cfx.common.exception.ServiceException 检查未通过抛出异常
     */
    void check(UserDetails check);

}
