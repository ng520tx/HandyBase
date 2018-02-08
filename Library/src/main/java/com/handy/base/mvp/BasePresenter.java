package com.handy.base.mvp;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Mvp框架中 Presenter层通用基类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Presenter层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public class BasePresenter<V extends IView> implements IPresenter, LifecycleObserver {
    protected CompositeDisposable mCompositeDisposable;

    protected V view;
    protected List<BaseModel> baseModels = new ArrayList<>();

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     */
    public BasePresenter(@NonNull List<BaseModel> baseModels, @NonNull V view) {
        this.baseModels = new ArrayList<>(baseModels);
        this.view = view;
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
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
        }
        if (ObjectUtils.isNotEmpty(baseModels)) {
            for (BaseModel baseModel : baseModels) {
                ((LifecycleOwner) view).getLifecycle().addObserver(baseModel);
            }
        }
    }

    @Override
    public void addModel(@NonNull BaseModel baseModel) {
        baseModels.add(baseModel);
        ((LifecycleOwner) view).getLifecycle().addObserver(baseModel);
    }

    /**
     * 在框架中 {@link Activity#onPause()} 且 {@link Activity#isFinishing()} == true 时会默认调用 {@link IPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        unDispose();
        if (ObjectUtils.isNotEmpty(baseModels)) {
            for (BaseModel baseModel : baseModels) {
                baseModel.onDestroy();
            }
            baseModels.clear();
        }
        this.view = null;
        this.mCompositeDisposable = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
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
