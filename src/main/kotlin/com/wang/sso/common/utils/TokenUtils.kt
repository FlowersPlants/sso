package com.wang.sso.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.wang.sso.core.security.base.SecurityUser
import com.wang.sso.modules.sys.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import kotlin.collections.HashMap

/**
 * token工具，常量可配置到配置文件
 */
object TokenUtils {
    private const val CLAIM_KEY_CREATED = "created"
    private const val CLAIM_KEY_SUBJECT = "subject"

    private const val DEFAULT_ALGORITHM = "hs256"
    private const val DEFAULT_SECRET = "wang@+!secret"

    // 过期时间 1800s，即30分钟
    private const val DEFAULT_EXPIRATION = 1800L
    // 设置类记住我的过期时间是 604800s，即7天
    private const val DEFAULT_EXPIRATION_REMEMBER_ME = 604800L

    private const val ALGORITHM = DEFAULT_ALGORITHM
    private const val SECRET = DEFAULT_SECRET
    private const val EXPIRATION = DEFAULT_EXPIRATION
    private const val EXPIRATION_REMEMBER_ME = DEFAULT_EXPIRATION_REMEMBER_ME

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
     * token解析，获取主体（用户信息，包括角色）
     */
    fun getSubjectFormToken(token: String): String? {
        return try {
            getClaimFormToken(token)?.get(CLAIM_KEY_SUBJECT).toString()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 返回security user
     */
    fun getUserBySubject(subject: String): SecurityUser {
        return ObjectMapper().readValue(subject, SecurityUser::class.java)
    }

    /**
     * 是否已过期
     */
    fun isExpiration(token: String): Boolean {
        return getClaimFormToken(token)!!.expiration.before(Date())
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
            .signWith(SignatureAlgorithm.forName(ALGORITHM), SECRET)
            .compact()
    }

    /**
     * token 解析
     */
    private fun getClaimFormToken(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
        } catch (e: Exception) {
            null
        }
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
        println(generateToken(ObjectMapper().writeValueAsString(user)))
    }
}