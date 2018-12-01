package com.wang.sso.core.security.user

import com.wang.sso.common.utils.JsonUtils
import com.wang.sso.modules.sys.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import kotlin.collections.HashMap

/**
 * token工具，常量可配置到配置文件
 * @author FlowersPlants
 * @since v1
 */
object TokenUtils {
    // 算法
    private const val DEFAULT_ALGORITHM = "hs256"
    // 密钥
    private const val DEFAULT_SECRET = "wang@+!secret"
    // 过期时间 1800s，即30分钟
    private const val DEFAULT_EXPIRATION = 1800L
    // 设置了记住我的过期时间是 604800s，即7天
    private const val DEFAULT_EXPIRATION_REMEMBER_ME = 604800L

    private const val CLAIM_KEY_CREATED = "created"
    private const val CLAIM_KEY_SUBJECT = "subject"

    private const val ALGORITHM = DEFAULT_ALGORITHM
    private const val SECRET = DEFAULT_SECRET
    private const val EXPIRATION = DEFAULT_EXPIRATION
    private const val EXPIRATION_REMEMBER_ME = DEFAULT_EXPIRATION_REMEMBER_ME

    /**
     * token 解析
     * 从令牌中获取数据声明
     */
    private fun getClaimFormToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
        } catch (e: Exception) {
            null
        }
    }

    /**
     * token解析，获取主体
     * @return 目前返回的是用户的唯一标识（account）
     */
    fun getSubjectFormToken(token: String): String? {
        return try {
            getClaimFormToken(token)?.get(CLAIM_KEY_SUBJECT).toString()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 是否已过期
     */
    fun isExpiration(token: String): Boolean {
        return getClaimFormToken(token)?.expiration?.before(Date()) ?: true
    }


    /**
     * 生成token，不记住我
     */
    fun generateToken(subject: String): String {
        val claims = HashMap<String, Any>().apply {
            this[CLAIM_KEY_CREATED] = Date()
            this[CLAIM_KEY_SUBJECT] = subject
        }
        return generateToken(claims)
    }

    /**
     * 生成token，记住我
     */
    fun generateToken(subject: String, isRememberMe: Boolean): String {
        val claims = HashMap<String, Any>().apply {
            this[CLAIM_KEY_CREATED] = Date()
            this[CLAIM_KEY_SUBJECT] = subject
        }
        return generateToken(claims, isRememberMe)
    }

    /**
     * 生成token
     */
    private fun generateToken(claims: Map<String, Any>): String {
        return generateToken(claims, false)
    }

    private fun generateToken(claims: Map<String, Any>, isRememberMe: Boolean): String {
        val expiration = if (isRememberMe) EXPIRATION_REMEMBER_ME else EXPIRATION
        return Jwts
            .builder()
            .setClaims(claims)
            .setExpiration(generateExpireDate(expiration))
            .signWith(
                SignatureAlgorithm.forName(ALGORITHM),
                SECRET
            )
            .compact()
    }

    private fun generateExpireDate(expiration: Long): Date {
        return Date(System.currentTimeMillis() + expiration * 1000)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val user = User().apply {
            account = "admin"
            password = "admin"
        }
        println(generateToken(JsonUtils.toJson(user)))
    }
}