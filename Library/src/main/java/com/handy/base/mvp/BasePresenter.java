package com.handy.base.mvp;

import android.app.Activity;
import android.app.Service;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.view.View;

import com.trello.rxlifecycle2.RxLifecycle;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Mvp框架中 Presenter层通用基类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Presenter层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {
    protected CompositeDisposable mCompositeDisposable;

    protected M model;
    protected V view;

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param view
     */
    public BasePresenter(@NonNull M model, @NonNull V view) {
        this.model = model;
        this.view = view;
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param view
     */
    public BasePresenter(@NonNull V view) {
        this.view = view;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (view != null && view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
            if (model != null && model instanceof LifecycleObserver) {
                ((LifecycleOwner) view).getLifecycle().addObserver((LifecycleObserver) model);
            }
        }
    }

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        //解除订阅
        unDispose();
        if (model != null) {
            model.onDestroy();
        }
        this.model = null;
        this.view = null;
        this.mCompositeDisposable = null;
    }

    /**
     * 只有当 {@code view} 不为 null, 并且 {@code view} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     * 所以当您想在 {@link Service} 以及一些自定义 {@link View} 或自定义类中使用 {@code Presenter} 时
     * 您也将不能继续使用 {@link OnLifecycleEvent} 绑定生命周期
     *
     * @param owner link {@link SupportActivity} and {@link Fragment}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        /**
         * 注意, 如果在这里调用了 {@link #onDestroy()} 方法, 会出现某些地方引用 {@code model} 或 {@code view} 为 null 的情况
         * 比如在 {@link RxLifecycle} 终止 {@link Observable} 时, 在 {@link io.reactivex.Observable#doFinally(Action)} 中却引用了 {@code view} 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 {@code model} 或 {@code view} 也可能会出现此情况
         */
        owner.getLifecycle().removeObserver(this);
    }

    @Override
    public void addDispose(@NonNull Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //将所有 Disposable 放入集中处理
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unDispose() {
        if (mCompositeDisposable != null) {
            //保证 Activity 结束时取消所有正在执行的订阅
            mCompositeDisposable.clear();
        }
    }
}
