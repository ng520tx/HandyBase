package com.handy.base.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuJie on 2016/12/26.
 */

class BaseAppApi {

    interface BaseAtyApi {
        /**
         * ===================================================================
         * 在onStart方法中被调用，用于初始化界面布局控件
         *
         * @param savedInstanceState 缓存数据
         */
        void initActivityView(@Nullable Bundle savedInstanceState);

        /**
         * 在onStart方法中且initActivityView执行完后被调用，用于初始化界面有关数据。
         */
        void initActivityData();

        /**
         * 在onResume方法中被调用，当界面对用户可见且isRequesting==true时执行此方法，用于可能重复执行的操作。
         */
        void onActivityRequest();


        /**
         * 在onStart方法中被调用，用户检查权限
         */
        void checkActivityPermissions();

        /**
         * 当权限扫描通过时（全部权限均已开启），执行此方法。若不许执行权限扫描或避免此方法。
         * <pre>
         * 注意：此方法的执行顺序是:
         * 子类 initActivityData()      super  前
         * 父类 initActivityData()      方法
         * 子类 onActivityPermission()  super  前
         * 父类 onActivityPermission()  方法
         * 子类 onActivityPermission()  super  后
         * 子类 initActivityData()      super  后
         * </pre>
         */
        void onActivityPermissionSuccess();

        /**
         * 当权限扫描不通过，且请求被拒绝时执行此方法。若不许执行权限扫描或避免此方法。
         */
        void onActivityPermissionRejection();
    }

    interface BaseFgmApi {
        /**
         * ===================================================================
         * 在onCreate方法中被调用，用于初始化界面布局控件
         */
        View initFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

        /**
         * 当initView执行完后被调用，用户初始化界面控件UI
         *
         * @param view               当前Fragment布局View
         * @param savedInstanceState 缓存数据
         */
        void completeFragmentView(View view, @Nullable Bundle savedInstanceState);

        /**
         * 在onCreate方法中且initView执行完后被调用，用于初始化界面有关数据。
         */
        void initFragmentData();

        /**
         * 在onResume方法中被调用，当界面对用户可见时被调用
         */
        void onFragmentRefresh();

        /**
         * 在onResume方法中被调用，当界面对用户可见且isRequesting==true时执行此方法，用于可能重复执行的操作。
         */
        void onFragmentRequest();
    }
}
