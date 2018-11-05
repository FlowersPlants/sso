package com.wang.sso.core.security

import com.wang.sso.core.exception.ExceptionEnum
import com.wang.sso.core.exception.ServiceException
import com.wang.sso.core.exception.SsoException
import com.wang.sso.core.support.BaseModel
import com.wang.sso.modules.sys.dao.IUserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * 自定义details service对象
 */
@Service
class SecurityUserService : UserDetailsService {

    @Autowired
    private lateinit var userDao: IUserDao

    /**
     * @param username 用户账号
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在
     */
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            val user = userDao.findUserByUsername(username)
            return when (user) {
                null -> throw ServiceException(ExceptionEnum.USERNAME_OR_PASSWORD_INCORRECT)
                else -> {
                    when (user.status) {
                        BaseModel.FREEZE -> throw ServiceException(ExceptionEnum.ACCOUNT_LOCKED)
                        BaseModel.DISABLE -> throw ServiceException(ExceptionEnum.ACCOUNT_DISABLED)
                        else -> {
                            SecurityUserFactory.create(user)
                        }
                    }
                }
            }
        } catch (e: SsoException) {
            throw SsoException(500, e.message!!)
        }
    }
}
