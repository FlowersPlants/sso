package com.wang.sso.core.security

import com.wang.sso.common.idgen.IdGenerate
import com.wang.sso.core.exception.SsoException
import com.wang.sso.core.support.BaseModel
import com.wang.sso.modules.sys.dao.IMenuDao
import com.wang.sso.modules.sys.dao.IRoleDao
import com.wang.sso.modules.sys.dao.IUserDao
import com.wang.sso.modules.sys.entity.Menu
import com.wang.sso.modules.sys.entity.User
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

/**
 * 自定义details service对象
 */
@Service
class SecurityUserService : UserDetailsService {

    @Autowired
    private lateinit var userDao: IUserDao

    @Autowired
    private lateinit var menuDao: IMenuDao

    @Autowired
    private lateinit var roleDao: IRoleDao

    /**
     * 需修改
     * @param username 用户账号
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在
     */
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            val user = userDao.findUserByUsername(username)
            return when (user) {
                null -> throw AuthenticationCredentialsNotFoundException("用户名或密码不正确")
                else -> {
                    when (user.status) {
                        BaseModel.FREEZE -> throw LockedException("账号被锁定，请联系管理员")
                        BaseModel.DISABLE -> throw DisabledException("账号已作废")
                        else -> {
                            val securityUser = SecurityUser()
                            BeanUtils.copyProperties(user, securityUser)
                            securityUser.account = user.account
                            securityUser.pwd = user.password
                            securityUser.loginTime = Date().time
                            securityUser.expireTime = 180000L
                            // securityUser.token = tokenDao.saveToken(securityUser).token
                            securityUser.token = IdGenerate.uuid()

                            securityUser.roles = roleDao.findByUserId(user.id)

                            val menuList = getListByUser(user)
                            securityUser.menus = menuList

                            securityUser
                        }
                    }
                }
            }
        } catch (e: SsoException) {
            throw SsoException(500, e.message!!)
        }
    }

    /**
     * 根据userID获取其所有权限，并去重
     */
    private fun getListByUser(user: User): MutableList<Menu> {
        var menuList = menuDao.findListByUserId(user.id)
        // 过滤
        menuList = ArrayList(LinkedHashSet<Menu>(menuList))
        return menuList
    }
}
