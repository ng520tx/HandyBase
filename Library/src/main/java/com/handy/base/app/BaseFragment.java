package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handy.base.utils.ScreenUtils;

import java.io.Serializable;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Fragment基类
 * </pre>
 */
public abstract class BaseFragment extends Fragment implements BaseAppApi.BaseFgmApi, Serializable {
    public View fragmentView;

    public Context context;
    public Activity activity;
    public Application application;

    public int screenWidth; //手机屏幕宽度参数
    public int screenHeight; //手机屏幕高度参数

    public boolean isAlive = false; //Fragment的活跃状态
    public boolean isInitViewHDB = true; //onViewCreated中初始化界面视图
    public boolean isInitDataHDB = true; //onActivityCreated中初始化界面视图
    public boolean isOnRequestHDB = true; //用于控制每个Fragment进入onResume时，是否重新执行onRequest()方法

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
        if (context == null) this.context = getContext();
        if (activity == null) this.activity = getActivity();

        this.isAlive = true;
        this.application = activity.getApplication();
        this.screenWidth = ScreenUtils.getInstance().getScreenWidth(context);
        this.screenHeight = ScreenUtils.getInstance().getScreenHeight(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            this.fragmentView = createViewHDB(inflater, container, savedInstanceState);
        }
        return this.fragmentView != null ? this.fragmentView : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentView == null)
            fragmentView = view;

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
        if (getUserVisibleHint()) {
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
        this.isAlive = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
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
}
