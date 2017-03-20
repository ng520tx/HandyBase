package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.handy.base.utils.ActivityStackUtils;

/**
 * Activity基本类
 * Created by LiuJie on 2016/4/22.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseAppApi.BaseAtyApi {
    public Context context;
    public Activity activity;
    public Application application;

    public int screenWidth = 0; //屏幕宽度
    public int screenHeight = 0; //屏幕高度
    public boolean canShowDialog = false; //用于控制在activity创建销或毁时，对Dialog的显隐控制
    public boolean isInitActivityView = true; //用于控制onStart中initView方法的执行。
    public boolean isInitActivityData = true; //用于控制onStart中initData方法的执行。
    public boolean isOnActivityRequest = true; //用于被其他activity改变后，退回到当前界面是否重新执行onRequest()方法
    public boolean isCheckActivityPermissions = true; //用于控制activity进入onStart时，是否扫描权限
    private Bundle savedInstanceState = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.activity = this;
        this.canShowDialog = true;
        this.application = getApplication();
        this.savedInstanceState = savedInstanceState;

        GetScreenSize();
        ActivityStackUtils.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isCheckActivityPermissions) {
            checkActivityPermissions();
            isCheckActivityPermissions = false;
        }

        if (isInitActivityView) {
            initActivityView(savedInstanceState);
            isInitActivityView = false;
        }

        if (isInitActivityData) {
            initActivityData();
            isInitActivityData = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnActivityRequest) { //对用户可见 已创建 重新读取
            onActivityRequest();
            isOnActivityRequest = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        canShowDialog = false;
        ActivityStackUtils.getInstance().finishActivity(this);
    }

    @Override
    public void initActivityView(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initActivityData() {

    }

    @Override
    public void checkActivityPermissions() {
        if (!PermissionsUT.getInstance().checkDeniedPermissions(activity, true)) {
            onActivityPermissionSuccess();
        }
    }

    @Override
    public void onActivityRequest() {

    }

    @Override
    public void onActivityPermissionSuccess() {

    }

    @Override
    public void onActivityPermissionRejection() {
//            SweetDialogUT.getInstance().showNormalDialog((BaseActivity) activity, "发现未启用权限", "为保障应用正常使用，请开启应用权限", "开启", "退出", new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    PrintfUT.getInstance().showShortToast(context, "请在手机设置权限管理中启用开启此应用系统权限");
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    intent.setData(Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, 45);
//                    sweetAlertDialog.dismiss();
//                }
//            }, new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    sweetAlertDialog.dismiss();
//                    ActivityStackUtils.getInstance().AppExit(context);
//                }
//            }).setCancelable(false);

//        //若从设置界面返回，重新扫描权限（请将此方法放与onActivityPermissionRejection()同级）
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == 45) {
//                PermissionsUT.getInstance().checkDeniedPermissions(activity, true);
//            }
//        }
    }

    /**
     * ===================================================================
     * 获取当前手机屏幕宽高参数
     */
    public void GetScreenSize() {
        //获取屏幕大小
        DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
        screenWidth = localDisplayMetrics.widthPixels;
        screenHeight = localDisplayMetrics.heightPixels;
    }

    /**
     * ===================================================================
     * 应用权限判断处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!PermissionsUT.getInstance().checkDeniedPermissions(activity, true)) {
                onActivityPermissionSuccess();
            }
        } else {
            onActivityPermissionRejection();
        }
    }
}