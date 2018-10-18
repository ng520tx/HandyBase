package com.handy.base.mvp;

import android.app.Activity;

import androidx.lifecycle.LifecycleObserver;


/**
 * Mvp框架中 Presenter层通用接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Presenter层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface IPresenter extends LifecycleObserver {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 向Presenter添加Model，用以批量绑定、解绑等
     *
     * @param iModel
     */
    void addIModel(IModel iModel);

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();
}
