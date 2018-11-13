package com.wang.sso.modules.core.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wang.sso.modules.sys.entity.Menu;
import com.wang.sso.modules.sys.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link com.wang.sso.core.security.user.SecurityUser}
 * @deprecated 使用kotlin包下的同名类代替
 *
 * @author FlowersPlants
 * @since v1
 */
public class SecurityUser extends User implements UserDetails {
    private static final long serialVersionUID = 1020034243435L;

    private String username;

    private String token;
    /**
     * 登陆时间戳（毫秒）
     */
    private Long loginTime;
    /**
     * 过期时间戳
     */
    private Long expireTime;

    /**
     * 所有权限
     */
    private List<Menu> menus;

    public SecurityUser() {

    }

    public SecurityUser(String username, List<Menu> menus) {
        this();
        this.username = username;
        this.menus = menus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    /**
     * @return 所有权限
     */
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return menus.parallelStream()
                .filter(p -> p.getId() != null && !"".equals(p.getId()))
                .map(p -> new SimpleGrantedAuthority(p.getId()))
                .collect(Collectors.toSet());
    }

    /**
     * 判断账号是否已经过期
     *
     * @return 默认没有过期
     */
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账号是否被锁定
     *
     * @return 默认没有锁定
     */
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断信用凭证是否过期
     *
     * @return 默认没有过期
     */
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否可用
     *
     * @return 默认可用
     */
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "SecurityUser{" +
                "id='" + getId() + '\'' +
                ", account='" + getAccount() + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", loginTime=" + loginTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
