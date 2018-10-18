package com.handy.base.app.dagger;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/11 下午4:41
 * @modified By LiuJie
 */
public class UserObject implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
