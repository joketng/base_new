package com.joketng.base.h5.ant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author THC
 * @Title: JCallBack
 * @Package com.jointem.hgp.html5
 * @Description:
 * @date 2016/12/21 14:48
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JCallBack {
}
