package com.handy.base.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/6 上午11:07
 * @modified By LiuJie
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ApplicationScope {
}
