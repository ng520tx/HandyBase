package com.handy.base.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Activity基本类接口
 * </pre>
 */
class BaseAppApi {
    interface BaseAtyApi {

        /**
         * <pre>
         *  初始化界面视图布局
         *  在onCreate被调用，被调用后若视图设置成功则
         * </pre>
         */
        boolean setContentViewHDB(@LayoutRes int layoutResId);

        /**
         * <pre>
         *  初始化意图内容数据
         *  在onStart中，当意图内容不为null且size大于0时被调用
         * </pre>
         */
        void initIntentBundle(Bundle intentBundle);

        /**
         * <pre>
         *  用户检查权限
         *  在onStart方法中被调用
         * </pre>
         */
        void checkPermissionsHDB();

        /**
         * <pre>
         *  初始化界面视图
         *  在onStart方法中被调用
         * </pre>
         */
        void initViewHDB(@Nullable Bundle savedInstanceState);

        /**
         * <pre>
         *  初始化界面数据
         *  在onStart方法中被调用
         * </pre>
         */
        void initDataHDB();

        /**
         * <pre>
         *  刷新界面视图或数据
         *  每次进入onResume方法时被调用
         * </pre>
         */
        void onRefreshHDB();

        /**
         * <pre>
         *  请求界面相关处理
         *  在onResume方法中，当界面对用户可见且isRequesting==true时执行此方法，用于可能重复执行的操作。
         * </pre>
         */
        void onRequestHDB();

        /**
         * <pre>
         *  当权限扫描通过时（全部权限均已开启）执行方法
         *  回调接口，若不执行权限扫描，此方法无用。
         *  若执行扫描，当安卓版本小于23或安卓版本大于等于23且权限均已开启时，会直接调用此方法。
         * </pre>
         */
        void onPermissionSuccessHDB();

        /**
         * <pre>
         *  当权限扫描不通过时执行方法
         *  回调接口，若不执行权限扫描，此方法无用。
         *  若执行扫描，当安卓版本大于等于23且发现未开启权限时，会直接调用此方法。
         * </pre>
         */
        void onPermissionRejectionHDB();
    }

    interface BaseFgmApi {
        /**
         * <pre>
         *  初始化界面视图
         *  在onCreateView方法中被调用。
         * </pre>
         */
        View createViewHDB(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

        /**
         * <pre>
         *  初始化界面控件
         *  在onViewCreated方法中被调用。
         * </pre>
         */
        void initViewHDB(View view, @Nullable Bundle savedInstanceState);

        /**
         * <pre>
         *  初始化界面数据
         *  在onActivityCreated方法中被调用。
         * </pre>
         */
        void initDataHDB(@Nullable Bundle savedInstanceState);

        /**
         * <pre>
         *  刷新界面视图或数据
         *  在onResume方法中被调用。
         * </pre>
         */
        void onRefreshHDB();

        /**
         * <pre>
         *  界面可见时相关处理
         *  在setUserVisibleHint方法中，当界面对用户可见时被调用。
         * </pre>
         */
        void onVisiableHDB();

        /**
         * <pre>
         *  请求界面相关处理
         *  在setUserVisibleHint方法中，当界面对用户可见且isRequesting==true时被调用。
         * </pre>
         */
        void onRequestHDB();
    }
}
