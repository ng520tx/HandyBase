package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Fragment基类
 * <p/>
 * Created by LiuJie on 2016/4/14.
 */
public abstract class BaseFragment extends Fragment implements BaseAppApi.BaseFgmApi, Serializable {
    public View fragmentView;

    public Activity activity;
    public Context context;
    public Application application;

    public int screenWidth; //手机屏幕宽度参数
    public int screenHeight; //手机屏幕高度参数

    public boolean canShowDialog = false; //用于控制在activity销毁后，取消对Dialog的操作
    public boolean isFragmentCreated = false; //用于控制每个Fragment时候完成被Activity的创建操作，若完成在onResume和setUserVisibleHint方法中可以执行onRefresh()或onRequest()方法。
    public boolean isInitFragmentData = true; //用于控制onActivityCreated中isFrgmData方法的执行。
    public boolean isOnFragmentRequest = true; //用于控制每个Fragment进入onResume时，是否重新执行onRequest()方法

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
        canShowDialog = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            this.fragmentView = initFragmentView(inflater, container, savedInstanceState);
        }
        return this.fragmentView != null ? this.fragmentView : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentView == null)
            fragmentView = view;
        completeFragmentView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFragmentCreated = true;
        if (isInitFragmentData) {
            initFragmentData();
            isInitFragmentData = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && isFragmentCreated) {
            onFragmentRefresh();
            if (isOnFragmentRequest) {
                onFragmentRequest();
                isOnFragmentRequest = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        canShowDialog = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFragmentCreated) {
            onFragmentRefresh();
            if (isOnFragmentRequest) {
                onFragmentRequest();
                isOnFragmentRequest = false;
            }
        }
    }

    /**
     * ===================================================================
     * 获取当前手机屏幕宽高参数
     */
    public void GetScreenSize() {
        DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
        this.screenWidth = localDisplayMetrics.widthPixels;
        this.screenHeight = localDisplayMetrics.heightPixels;
    }

    @Override
    public View initFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void completeFragmentView(View view, @Nullable Bundle savedInstanceState) {
        GetScreenSize();
        this.application = getActivity().getApplication();
    }

    @Override
    public void initFragmentData() {

    }

    @Override
    public void onFragmentRefresh() {

    }

    @Override
    public void onFragmentRequest() {

    }
}
