package com.handy.base.mvp;

import android.app.Activity;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Mvp框架中 Presenter层通用基类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 所有Presenter层均需要实现此接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public class BasePresenter<V extends IView> implements IPresenter {

    protected V view;
    protected List<IModel> iModels = new ArrayList<>();

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     */
    public BasePresenter(@NonNull List<IModel> iModels, @NonNull V view) {
        this.iModels = new ArrayList<>(iModels);
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
        if (ObjectUtils.isNotEmpty(iModels)) {
            for (IModel iModel : iModels) {
                ((LifecycleOwner) view).getLifecycle().addObserver(iModel);
            }
        }
    }

    @Override
    public void addIModel(@NonNull IModel iModel) {
        iModels.add(iModel);
        ((LifecycleOwner) view).getLifecycle().addObserver(iModel);
    }

    /**
     * 在框架中 {@link Activity#onPause()} 且 {@link Activity#isFinishing()} == true 时会默认调用 {@link IPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        if (ObjectUtils.isNotEmpty(iModels)) {
            for (IModel iModel : iModels) {
                iModel.onDestroy();
            }
            iModels.clear();
        }
        this.view = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
