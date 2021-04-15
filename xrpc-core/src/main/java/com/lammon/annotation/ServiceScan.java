package com.lammon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识服务扫描的根包,默认为空
 *
 * @author lammon
 * @date 2021/4/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScan {
    String servicePackage() default "";
}
