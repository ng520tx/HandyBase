package com.handy.base.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class BaseAppApi {

    interface BaseAtyApi {

        void initIntentBundle(Bundle intentBundle);

        /**
         * 在onStart方法中被调用，用于初始化界面布局控件
         *
         */
        void initHDBView(@Nullable Bundle savedInstanceState);

        /**
         * 在onStart方法中且initView执行完后被调用，用于初始化界面有关数据。
         */
        void initHDBData();

        /**
         * 在onResume方法中被调用，当调用onResume方法时被调用
         */
        void onHDBRefresh();

        /**
         * 在onResume方法中被调用，当界面对用户可见且isRequesting==true时执行此方法，用于可能重复执行的操作。
         */
        void onHDBRequest();


        /**
         * 在onStart方法中被调用，用户检查权限
         */
        void checkHDBPermissions();

        /**
         * 当权限扫描通过时（全部权限均已开启），执行此方法。若不许执行权限扫描或避免此方法。
         * <pre>
         * 注意：此方法的执行顺序是:
         * 子类 initHDBData()      super  前
         * 父类 initHDBData()      方法
         * 子类 onPermission()  super  前
         * 父类 onPermission()  方法
         * 子类 onPermission()  super  后
         * 子类 initHDBData()      super  后
         * </pre>
         */
        void onHDBPermissionSuccess();

        /**
         * 当权限扫描不通过，且请求被拒绝时执行此方法。若不许执行权限扫描或避免此方法。
         */
        void onHDBPermissionRejection();
    }

    interface BaseFgmApi {
        /**
         * ===================================================================
         * 在onCreate方法中被调用，用于初始化界面布局控件
         */
        View initHDBView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

        /**
         * 当initView执行完后被调用，用户初始化界面控件UI
         *
         * @param view               当前布局View
         * @param savedInstanceState 缓存数据
         */
        void onHDBComplete(View view, @Nullable Bundle savedInstanceState);

        /**
         * 在onCreate方法中且initView执行完后被调用，用于初始化界面有关数据。
         */
        void initHDBData();

        /**
         * 在onResume方法中被调用，当调用onResume方法时被调用
         */
        void onHDBRefresh();

        /**
         * 在setUserVisibleHint方法中被调用，当界面对用户可见时被调用
         */
        void onHDBVisiable();

        /**
         * 在onResume方法中被调用，当界面对用户可见且isRequesting==true时执行此方法，用于可能重复执行的操作。
         */
        void onHDBRequest();
    }
}
