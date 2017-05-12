package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.handy.base.utils.ActivityStackUtils;
import com.handy.base.utils.PermissionsUtils;
import com.handy.base.utils.ScreenUtils;
import com.handy.base.utils.Utils;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/liujie045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Activity基本类
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseAppApi.BaseAtyApi {
    public Context context;
    public Activity activity;
    public Application application;

    public int screenWidth = 0; //屏幕宽度
    public int screenHeight = 0; //屏幕高度
    public View contentView = null; //界面视图布局

    public boolean isAlive = false; //Activity的活跃状态
    public boolean isInitViewHDB = true; //onStart中初始化界面视图
    public boolean isInitDataHDB = true; //onStart中初始化界面数据
    public boolean isOnRequestHDB = true; //onResume中界面请求处理
    public boolean isInitIntentBundle = true; //onStart中初始化意图内容
    public boolean isCheckPermissionsHDB = true; //onStart中权限扫描

    public Bundle intentBundle = null;
    public Bundle savedInstanceState = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.isAlive = true;
            this.context = this;
            this.activity = this;
            this.application = getApplication();
            this.savedInstanceState = savedInstanceState;
            this.screenWidth = ScreenUtils.getScreenWidth();
            this.screenHeight = ScreenUtils.getScreenHeight();

            Utils.init(this);
            ActivityStackUtils.addActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /* 初始化Activity接收意图的内容 */
        if (isInitIntentBundle) {
            if (getIntent().getExtras() != null && getIntent().getExtras().size() > 0) {
                this.intentBundle = getIntent().getExtras();
                initIntentBundle(this.intentBundle);
            }
            isInitIntentBundle = false;
        }
        /* 安卓权限扫描 */
        if (isCheckPermissionsHDB) {
            checkPermissionsHDB();
            isCheckPermissionsHDB = false;
        }
        /* 初始化界面视图 */
        if (isInitViewHDB) {
            initViewHDB(savedInstanceState);
            isInitViewHDB = false;
        }
        /* 初始化界面数据 */
        if (isInitDataHDB) {
            initDataHDB();
            isInitDataHDB = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Activity刷新时调用 */
        onRefreshHDB();
        /* Activity请求时调用（可被重复调用） */
        if (isOnRequestHDB) {
            onRequestHDB();
            isOnRequestHDB = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            isAlive = false;
            ActivityStackUtils.finishChoiceDesc(this);
        }
    }

    @Override
    public boolean setContentViewHDB(@LayoutRes int layoutResId) {
        contentView = LayoutInflater.from(context).inflate(layoutResId, null);
        if (contentView != null) {
            setContentView(contentView);
            return true;
        }
        return false;
    }

    @Override
    public void initIntentBundle(Bundle intentBundle) {
    }

    @Override
    public void checkPermissionsHDB() {
        if (!PermissionsUtils.checkDeniedPermissions(activity, true)) {
            onPermissionSuccessHDB();
        }
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initDataHDB() {
    }

    @Override
    public void onRefreshHDB() {
    }

    @Override
    public void onRequestHDB() {
    }

    @Override
    public void onPermissionSuccessHDB() {
    }

    @Override
    public void onPermissionRejectionHDB() {
        /*
         * 发现未启用的权限时，可以参考一下进行处理。
         * SweetDialogUT.showNormalDialog((BaseActivity) activity, "发现未启用权限", "为保障应用正常使用，请开启应用权限", "开启", "退出", new SweetAlertDialog.OnSweetClickListener() {
         *     @Override
         *     public void onClick(SweetAlertDialog sweetAlertDialog) {
         *         PrintfUT.showShortToast(context, "请在手机设置权限管理中启用开启此应用系统权限");
         *         Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
         *         intent.setData(Uri.parse("package:" + getPackageName()));
         *         startActivityForResult(intent, 45);
         *         sweetAlertDialog.dismiss();
         *     }
         * }, new SweetAlertDialog.OnSweetClickListener() {
         *     @Override
         *     public void onClick(SweetAlertDialog sweetAlertDialog) {
         *         sweetAlertDialog.dismiss();
         *         ActivityStackUtils.AppExit(context);
         *     }
         * }).setCancelable(false);
         * //若从设置界面返回，重新扫描权限（请将此方法放与onActivityPermissionRejection()同级）
         * @Override
         * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         *     super.onActivityResult(requestCode, resultCode, data);
         *     if (requestCode == 45) {
         *         PermissionsUtils.checkDeniedPermissions(activity, true);
         *     }
         * }
         */
    }

    /**
     * ===================================================================
     * 应用权限判断处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!PermissionsUtils.checkDeniedPermissions(activity, true)) {
                onPermissionSuccessHDB();
            }
        } else {
            onPermissionRejectionHDB();
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putSerializable("ActivityStack", ActivityStackUtils.getActivityStack());
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//        if (savedInstanceState != null && savedInstanceState.size() > 0) {
//            ActivityStackUtils.setActivityStack((Stack<Activity>) savedInstanceState.getSerializable("ActivityStack"));
//        }
//    }
}