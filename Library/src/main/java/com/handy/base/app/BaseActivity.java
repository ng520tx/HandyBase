package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.handy.base.R;
import com.handy.base.rxjava.lifecycle.ActivityLifecycleable;
import com.handy.base.utils.ActivityStackUtils;
import com.handy.base.utils.PermissionsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Activity基本类
 * </pre>
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseApplicationApi.BaseActivityApi, ActivityLifecycleable {
    /**
     * 屏幕宽度
     */
    public int screenWidth = 0;
    /**
     * 屏幕高度
     */
    public int screenHeight = 0;
    /**
     * 界面视图布局
     */
    public View rootLayout = null;
    /**
     * onStart中初始化界面视图
     */
    public boolean isInitViewHDB = true;
    /**
     * onStart中初始化界面数据
     */
    public boolean isInitDataHDB = true;
    /**
     * onResume中界面请求处理
     */
    public boolean isOnRequestHDB = true;
    /**
     * onStart中初始化意图内容
     */
    public boolean isInitIntentBundle = true;
    /**
     * onStart中权限扫描
     */
    public boolean isCheckPermissionsHDB = true;
    /**
     * 是否使用作化返回功能
     */
    public boolean isUseSwipeBackFinish = false;

    public Context context;
    public Activity activity;
    public Application application;

    public Bundle intentBundle = null;
    public Bundle savedInstanceState = null;

    public BGASwipeBackHelper mSwipeBackHelper;

    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.context = this;
            this.activity = this;
            this.application = getApplication();
            this.savedInstanceState = savedInstanceState;
            this.screenWidth = ScreenUtils.getScreenWidth();
            this.screenHeight = ScreenUtils.getScreenHeight();

            initSwipeBackFinish();

            ActivityStackUtils.addActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /* 初始化Activity接收意图的内容 */
        if (isInitIntentBundle && getIntent() != null) {
            this.intentBundle = getIntent().getExtras();
            initIntentBundle(this.intentBundle);
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
            onFinishing();
            ActivityStackUtils.finishChoiceDesc(this);
        }
    }

    /**
     * RxJava 任务生命周期关联绑定
     */
    @NonNull
    @Override
    public Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    /**
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

    /**
     * 初始化滑动返回功能。此功能的启用控制可在 {@link Activity#onCreate(Bundle)} 的 super.onCreate(savedInstanceState) 之前或构造方法中设置全局变量 {@link BaseActivity#isUseSwipeBackFinish}
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, new BGASwipeBackHelper.Delegate() {
            @Override
            public boolean isSupportSwipeBack() {
                return isUseSwipeBackFinish;
            }

            @Override
            public void onSwipeBackLayoutSlide(float slideOffset) {

            }

            @Override
            public void onSwipeBackLayoutCancel() {

            }

            @Override
            public void onSwipeBackLayoutExecuted() {
                mSwipeBackHelper.swipeBackward();
            }
        });
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    @Override
    public boolean setContentViewHDB(@LayoutRes int layoutResId) {
        rootLayout = LayoutInflater.from(context).inflate(layoutResId, null);
        if (rootLayout != null) {
            setContentView(rootLayout);
            return true;
        }
        return false;
    }

    @Override
    public void initIntentBundle(Bundle bundle) {
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
    public void onFinishing() {
    }

    @Override
    public void onPermissionSuccessHDB() {
    }

    @Override
    public void onPermissionRejectionHDB() {
        /*
         * //发现未启用的权限时，可以参考一下进行处理。
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
}