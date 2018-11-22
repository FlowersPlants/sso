package com.wang.sso.modules.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mybatis注解，标注需要Springboot扫描的dao接口
 * @deprecated 不建议使用此注解，可用kotlin下的{@link com.wang.sso.core.mybatis.annotation.MyBatisDao}代替
 * @author FlowersPlants
 * @since v0
 */
@Deprecated
//@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBatisDao {
    String value() default "";

    Class<?> entity() default Class.class;
}
