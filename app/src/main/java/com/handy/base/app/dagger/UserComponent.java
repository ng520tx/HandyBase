package com.handy.base.app.dagger;

import dagger.Component;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/2 上午1:59
 * @modified By LiuJie
 */
@Component(modules = {UserModule.class})
public interface UserComponent {
    void inject(UserActivity userActivity);
}
