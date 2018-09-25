package com.joketng.base.h5.ant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author THC
 * @Title: Param
 * @Package com.jointem.hgp.html5
 * @Description:
 * @date 2016/12/21 9:40
 */
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Param {
    String value() default "";
}
