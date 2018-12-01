package com.wang.sso.core.security.user

import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.SsoSecurityException
import com.wang.sso.core.support.BaseModel
import com.wang.sso.modules.sys.utils.UserUtils
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * 自定义details service对象
 * @author FlowersPlants
 * @since v1
 */
@Service
class SecurityUserService : UserDetailsService {
    /**
     * @param username 用户账号
     * @return 用户信息,先从缓存获取
     * @throws AuthenticationException 统一抛出认证相关异常
     */
    @Throws(AuthenticationException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = UserUtils.findUserByAccount(username)
        return if (user == null) {
            throw SsoSecurityException(ExceptionEnum.USERNAME_OR_PASSWORD_INCORRECT)
        } else {
            when (user.status) {
                BaseModel.FREEZE -> throw SsoSecurityException(ExceptionEnum.ACCOUNT_LOCKED)
                BaseModel.DISABLE -> throw SsoSecurityException(ExceptionEnum.ACCOUNT_DISABLED)
                BaseModel.DELETE -> throw SsoSecurityException(603, "账号已被删除")
                else -> {
                    SecurityUserFactory.create(user)
                }
            }
        }
    }
}
