package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.io.Serializable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Fragment基类
 * </pre>
 */
public abstract class BaseFragment extends RxFragment implements BaseApplicationApi.BaseFragmentApi, BaseApplicationApi.BaseRxJavaApi, Serializable {
    /**
     * 手机屏幕宽度参数
     */
    public int screenWidth;
    /**
     * 手机屏幕高度参数
     */
    public int screenHeight;

    /**
     * Fragment的活跃状态
     */
    public boolean isAlive = false;
    /**
     * onViewCreated中初始化界面视图
     */
    public boolean isInitViewHDB = true;
    /**
     * onActivityCreated中初始化界面视图
     */
    public boolean isInitDataHDB = true;
    /**
     * 用于控制每个Fragment进入onResume时，是否重新执行onRequest()方法
     */
    public boolean isOnRequestHDB = true;

    public View rootLayout;

    public Context context;
    public Activity activity;
    public Application application;

    public CompositeDisposable compositeDisposable = null;

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

        this.isAlive = true;
        this.application = activity.getApplication();
        this.screenWidth = ScreenUtils.getScreenWidth();
        this.screenHeight = ScreenUtils.getScreenHeight();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootLayout == null) {
            this.rootLayout = createViewHDB(inflater, container, savedInstanceState);
        }
        return this.rootLayout != null ? this.rootLayout : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (rootLayout == null) {
            rootLayout = view;
        }

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
        if (getUserVisibleHint() && isAlive) {
            onRefreshHDB();
            if (isOnRequestHDB) {
                onRequestHDB();
                isOnRequestHDB = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearRxDisposable();

        this.isAlive = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isAlive) {
            onVisiableHDB();
            if (isOnRequestHDB) {
                onRequestHDB();
                isOnRequestHDB = false;
            }
        }
    }

    @Override
    public View createViewHDB(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
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
    public void onRequestHDB() {

    }

    @Override
    public void onVisiableHDB() {

    }

    @Override
    public void addRxDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void clearRxDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}
