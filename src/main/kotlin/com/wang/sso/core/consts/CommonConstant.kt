package com.wang.sso.core.consts

/**
 * 公共常量
 * @author FlowersPlants
 * @since v1
 */
object CommonConstant {

    // jwt token
    const val JWT_TOKEN_HEADER = "Authorization"
    const val JWT_TOKEN_HEAD = "Bearer " // 有个空格

    /**
     * 自定义登录参数名称
     * <p>account：登录账号，pwd：登录密码，rememberMe：记住我，validateCode：验证码，mobile：手机号</p>
     */
    const val PARAMETER_USERNAME = "account"
    const val PARAMETER_PASSWORD = "pwd"
    const val PARAMETER_REMEMBER_ME = "rememberMe"
    const val PARAMETER_VALIDATE_CODE = "validateCode"
    const val PARAMETER_MOBILE = "mobile"

    // other
    const val DEFAULT_ADMIN_ID = "1" // 默认超级管理员的ID为1
    const val DEFAULT_SORT = 10 // 默认排序号的值
}