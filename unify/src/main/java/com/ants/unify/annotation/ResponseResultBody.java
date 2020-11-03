package com.ants.unify.annotation;


import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @author lyy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {
}
