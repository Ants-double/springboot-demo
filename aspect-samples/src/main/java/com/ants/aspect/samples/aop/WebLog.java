package com.ants.aspect.samples.aop;

import java.lang.annotation.*;

/**
 * @author lyy08
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
