package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.handy.base.rxjava.FragmentLifecycleable;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Fragment基类
 * </pre>
 */
public abstract class BaseFragment extends RxFragment implements BaseApplicationApi.BaseFragmentApi, FragmentLifecycleable {
    /**
     * 手机屏幕宽度参数
     */
    public int screenWidth;
    /**
     * 手机屏幕高度参数
     */
    public int screenHeight;
    /**
     * 界面视图布局
     */
    public View rootLayout;
    /**
     * Fragment是否已创建成功，用于控制在Fragment创建之前不调用 {@link BaseFragment#setUserVisibleHint(boolean)} 方法实现内部的 {@link BaseFragment#onVisiableHDB()} ()} 和 {@link BaseFragment#onLazyLoadHDB()} 方法
     */
    public boolean isCreateed = false;
    /**
     * onViewCreated中初始化界面视图
     */
    public boolean isInitViewHDB = true;
    /**
     * onActivityCreated中初始化界面数据
     */
    public boolean isInitDataHDB = true;
    /**
     * 用于控制每个Fragment进入{@link BaseFragment#setUserVisibleHint(boolean)} 时，是否重新执行onRequest()方法
     */
    public boolean isLazyLoadHDB = true;

    public Context context;
    public Activity activity;
    public Application application;

    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (context == null) {
            this.context = getContext();
        }
        if (activity == null) {
            this.activity = getActivity();
        }

        assert activity != null;
        this.application = activity.getApplication();
        this.screenWidth = ScreenUtils.getScreenWidth();
        this.screenHeight = ScreenUtils.getScreenHeight();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.isCreateed = true;
        if (rootLayout == null) {
            rootLayout = view;
        }
        /* 初始化界面数据 */
        if (isInitViewHDB) {
            initViewHDB(view, savedInstanceState);
            isInitViewHDB = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isInitDataHDB) {
            initDataHDB(savedInstanceState);
            isInitDataHDB = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && isCreateed) {
            onRefreshHDB();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateed) {
            onVisiableHDB();
            if (isLazyLoadHDB) {
                onLazyLoadHDB();
                isLazyLoadHDB = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isCreateed = false;
    }

    @NonNull
    @Override
    public Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void initViewHDB(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initDataHDB(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onRefreshHDB() {

    }

    @Override
    public void onLazyLoadHDB() {

    }

    @Override
    public void onVisiableHDB() {

    }
}
