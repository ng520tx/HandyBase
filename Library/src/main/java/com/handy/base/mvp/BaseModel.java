package com.handy.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;


/**
 * Mvp框架中 Model层通用基类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Model层均需要实现此接口.子类主要用于数据的查询、解析，以及向 {@link BasePresenter} 层输出数据对象或者Oberable（RxJava上游对象）等。
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public class BaseModel implements IModel, LifecycleObserver {

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    @Override
    public void onDestroy() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
