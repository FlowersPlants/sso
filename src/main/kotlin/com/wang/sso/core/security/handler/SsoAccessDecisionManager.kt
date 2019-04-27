package com.wang.sso.core.security.handler

import com.wang.sso.core.exception.SsoSecurityException
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication

//import org.springframework.stereotype.Component


/**
 *Security需要用到一个实现了AccessDecisionManager接口的类
 * 类功能：根据当前用户的信息，和目标url涉及到的权限，判断用户是否可以访问
 * 判断规则：用户只要匹配到目标url权限中的一个role就可以访问
 */
//@Component
class SsoAccessDecisionManager : AccessDecisionManager {
    /**
     * decide 方法是判定是否拥有权限的决策方法，
     * authentication 是SystemService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * object 包含客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * configAttributes 为InvocationSecurityMetadataSourceService的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     */
    @Throws(AccessDeniedException::class, InsufficientAuthenticationException::class)
    override fun decide(authentication: Authentication, o: Any, configAttributes: Collection<ConfigAttribute>?) {
        if (null == configAttributes || configAttributes.isEmpty()) {
            return
        }
        var c: ConfigAttribute
        var needRole: String
        val iter = configAttributes.iterator()
        while (iter.hasNext()) {
            c = iter.next()
            needRole = c.attribute
            for (ga in authentication.authorities) {//authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
                if (needRole.trim { it <= ' ' } == ga.authority) {
                    return
                }
            }
        }

        //执行到这里，说明没有匹配到应有到权限
        throw SsoSecurityException(403, "权限不足！")
    }

    override fun supports(configAttribute: ConfigAttribute): Boolean {
        return true
    }

    override fun supports(aClass: Class<*>): Boolean {
        return true
    }
}
