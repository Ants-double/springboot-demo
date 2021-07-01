package com.ants.nap.aop;

import java.lang.annotation.*;

/**
 * @author everise
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    /**
     * 日志描述信息
     *
     * @return
     */
    String description() default "";

}
