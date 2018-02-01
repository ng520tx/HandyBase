package com.handy.base.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Activity基本类接口
 * </pre>
 */
public class BaseApplicationApi {
    interface BaseActivityApi {
        /**
         * <h4>绑定界面视图布局</h4>
         * 替换 {@link BaseActivity#onCreate(Bundle)} 实现里的 setContentView()方法，替换后可使用 {@link BaseActivity#rootLayout} 根布局。
         */
        boolean setContentViewHDB(@LayoutRes int layoutResId);

        /**
         * <h4>加载 {@link BaseActivity#getIntent()} 内容数据</h4>
         * 在onStart中，当意图内容不为null且size大于0时被调用。<br>
         * 方法的实现中需要注意判断bundle是否为null：
         * <pre>
         * if (bundle != null && bundle.size() > 0) {
         *     ......
         * }
         * </pre>
         */
        void initIntentBundle(Bundle intentBundle);

        /**
         * <h4>适配Android6.0，检查应用权限</h4>
         * 在 {@link BaseActivity#onStart()} 方法中被调用，建议只在第一个启动的 Activity 检查权限。
         */
        void checkPermissionsHDB();

        /**
         * <h4>初始化界面控件</h4>
         * 在 {@link BaseActivity#onStart()} 方法中被调用，用于绑定布局控件、初始化控件点击事件等。
         */
        void initViewHDB(@Nullable Bundle savedInstanceState);

        /**
         * <h4>初始化界面数据</h4>
         * 在 {@link BaseActivity#onStart()} 方法中被调用，用于控件数据加载、全局变量实例化等。
         */
        void initDataHDB();

        /**
         * <h4>刷新界面视图或数据</h4>
         * 在 {@link BaseActivity#onResume()} 方法中被调用，用于每次重新加载界面时加载数据、布局等。
         */
        void onRefreshHDB();

        /**
         * <h4>界面事务单次请求</h4>
         * 在 {@link BaseActivity#onResume()} 方法中被调用，并由全局变量 {@link BaseActivity#isOnRequestHDB}控制在界面重新加载时是否执行，用于单次加载数据等。
         */
        void onRequestHDB();

        /**
         * <h4>界面结束销毁时事务处理</h4>
         * 在{@link BaseActivity#onPause()} 方法中当 {@link BaseActivity#isFinishing()} ==true时调用此方法。
         */
        void onFinishing();

        /**
         * <h4>权限扫描通过时事务处理</h4>
         * 若不执行权限扫描，此方法无用。<br>
         * 若执行扫描，当安卓版本小于23或安卓版本大于等于23且权限均已开启时调用此方法。
         */
        void onPermissionSuccessHDB();

        /**
         * <h4>权限扫描不通过时事务处理</h4>
         * 若不执行权限扫描，此方法无用。<br>
         * 若执行扫描，当安卓版本大于等于23且发现有未开启的权限时调用此方法。用于弹出提示框提示用户在系统应用设置界面中手动开启权限。
         */
        void onPermissionRejectionHDB();
    }

    interface BaseFragmentApi {
        /**
         * <h4>初始化界面控件</h4>
         * 在 {@link BaseFragment#onViewCreated(View, Bundle)} 方法中被调用，用于绑定布局控件、初始化控件点击事件等。
         */
        void initViewHDB(View view, @Nullable Bundle savedInstanceState);

        /**
         * <h4>初始化界面数据</h4>
         * 在 {@link BaseFragment#onActivityCreated(Bundle)} 方法中被调用，用于控件数据加载、全局变量实例化等。
         */
        void initDataHDB(@Nullable Bundle savedInstanceState);

        /**
         * <h4>刷新界面视图或数据</h4>
         * 在 {@link BaseFragment#onResume()} 方法中被调用，用于每次重新加载界面时加载数据、布局等。
         */
        void onRefreshHDB();

        /**
         * <h4>界面可见时相关处理</h4>
         * 在 {@link BaseFragment#setUserVisibleHint(boolean)} 方法中被调用，用于每次界面可见时加载数据、布局等。
         */
        void onVisiableHDB();

        /**
         * <h4>Fragment懒加载方法</h4>
         * 当界面可见且当前Fragment已执行{@link BaseFragment#onViewCreated(View, Bundle)} 时，在 {@link BaseFragment#setUserVisibleHint(boolean)} ()} 方法中被调用，用于Fragment懒加载事务处理。
         */
        void onLazyLoadHDB();

        /**
         * <h4>界面结束销毁时事务处理</h4>
         * 在{@link BaseFragment#onDestroy()} 方法中被调用。
         */
        void onFinishing();
    }
}
