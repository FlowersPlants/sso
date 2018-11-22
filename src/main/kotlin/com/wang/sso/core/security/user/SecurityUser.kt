package com.wang.sso.core.security.user

import com.wang.sso.core.exception.SsoException
import com.wang.sso.core.support.BaseModel
import com.wang.sso.modules.sys.entity.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

/**
 * 系统安全认证用户
 * @author FlowersPlants
 * @since v1
 */
class SecurityUser : UserDetails {
    companion object {
        private const val serialVersionUID = 1020034243435L
    }

    /**
     * ID
     */
    var id: String? = null

    /**
     * 状态
     */
    var status: String? = null

    /**
     * 账号，kotlin方式用username报错
     */
    var account: String? = null

    /**
     * 密码，kotlin方式用password报错
     */
    var pwd: String? = null

    /**
     * 所有角色
     */
    var roles: MutableList<Role>? = null

    override fun getUsername(): String {
        return this.account ?: throw SsoException()
    }

    override fun getPassword(): String {
        return this.pwd ?: throw SsoException()
    }

    /**
     * @return 所有权限
     */
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles!!.parallelStream()
            .filter { p -> !p.enname.isNullOrEmpty() }
            .map { p -> SimpleGrantedAuthority("ROLE_${p.enname}") }
            .collect(Collectors.toSet()) // 此处类型推断失败？
    }

    // -----------     以下方法目前直接返回，后期添加逻辑        --------------
    /**
     * 判断账号是否已经过期
     * @return 默认没有过期
     */
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    /**
     * 判断账号是否被锁定
     * @return 状态是否为冻结
     */
    override fun isAccountNonLocked(): Boolean {
        return this.status != BaseModel.FREEZE
    }

    /**
     * 判断信用凭证是否过期
     * @return 默认没有过期
     */
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    /**
     * 判断账号是否可用
     * @return 状态是否为正常
     */
    override fun isEnabled(): Boolean {
        return this.status == BaseModel.NORMAL
    }
}