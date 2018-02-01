package com.handy.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.handy.base.retrofit.IRetrofitManager;


/**
 * Mvp框架中 Model层通用基类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Model层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public class BaseModel implements IModel, LifecycleObserver {
    /**
     * 用于管理网络请求层,以及数据缓存层
     */
    protected IRetrofitManager retrofitManager;

    public BaseModel(IRetrofitManager retrofitManager) {
        this.retrofitManager = retrofitManager;
    }

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    @Override
    public void onDestroy() {
        retrofitManager = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
