package com.wang.sso.common.utils

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component


/**
 * Spring工具类，获取bean对象
 */
@Component
class SpringUtils : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringUtils.applicationContext = applicationContext
    }

    companion object {

        private var applicationContext: ApplicationContext? = null

        fun <T> getBean(cla: Class<T>): T {
            return applicationContext!!.getBean(cla)
        }

        fun <T> getBean(name: String, cal: Class<T>): T {
            return applicationContext!!.getBean(name, cal)
        }

        fun getProperty(key: String): String? {
            return applicationContext!!.getBean(Environment::class.java).getProperty(key)
        }
    }
}