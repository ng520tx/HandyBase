package com.handy.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.handy.base.mvp.IPresenter;
import com.handy.base.rxjava.lifecycle.FragmentLifecycleable;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * <pre>
 *  author: Handy
 *  blog  : https://github.com/handy045
 *  time  : 2017-4-18 10:14:23
 *  desc  : Fragment基类
 * </pre>
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements BaseApplicationApi.BaseFragmentApi, FragmentLifecycleable, LifecycleProvider<FragmentEvent> {
    /**
     * 手机屏幕宽度参数
     */
    public int screenWidth;
    /**
     * 手机屏幕高度参数
     */
    public int screenHeight;
    /**
     * 界面视图布局
     */
    public View rootLayout;
    /**
     * Fragment是否已创建成功，用于控制在Fragment创建之前不调用 {@link BaseFragment#setUserVisibleHint(boolean)} 方法实现内部的 {@link BaseFragment#onVisiableHDB()} ()} 和 {@link BaseFragment#onLazyLoadHDB()} 方法
     */
    public boolean isCreateed = false;
    /**
     * onViewCreated中初始化界面视图
     */
    public boolean isInitViewHDB = true;
    /**
     * onActivityCreated中初始化界面数据
     */
    public boolean isInitDataHDB = true;
    /**
     * 用于控制每个Fragment进入{@link BaseFragment#setUserVisibleHint(boolean)} 时，是否重新执行onRequest()方法
     */
    public boolean isLazyLoadHDB = true;
    /**
     * 是否Log打印Fragment的生命周期
     */
    public boolean isLogFragmentLife = false;

    @Inject
    protected P presenter;

    public Context context;
    public Activity activity;
    public Application application;

    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public void onAttach(Activity activity) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onAttach(Activity activity)");
        }
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void onAttach(Context context) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onAttach(Context context)");
        }
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);

        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onCreate(Bundle savedInstanceState)");
        }
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);

        if (context == null) {
            this.context = getContext();
        }
        if (activity == null) {
            this.activity = getActivity();
        }

        assert activity != null;
        this.application = activity.getApplication();
        this.screenWidth = ScreenUtils.getScreenWidth();
        this.screenHeight = ScreenUtils.getScreenHeight();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onViewCreated(View view, Bundle savedInstanceState)");
        }
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);

        this.isCreateed = true;
        if (rootLayout == null) {
            rootLayout = view;
        }
        /* 初始化界面数据 */
        if (isInitViewHDB) {
            initViewHDB(view, savedInstanceState);
            isInitViewHDB = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onActivityCreated(Bundle savedInstanceState)");
        }
        super.onActivityCreated(savedInstanceState);

        if (isInitDataHDB) {
            initDataHDB(savedInstanceState);
            isInitDataHDB = false;
        }
    }

    @Override
    public void onStart() {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onStart()");
        }
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onResume()");
        }
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);

        if (getUserVisibleHint() && isCreateed) {
            onRefreshHDB();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - setUserVisibleHint(" + String.valueOf(isVisibleToUser) + ")");
        }
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isCreateed) {
            onVisiableHDB();
            if (isLazyLoadHDB) {
                onLazyLoadHDB();
                isLazyLoadHDB = false;
            }
        }
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onPause()");
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onStop()");
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onDestroyView()");
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onDestroy()");
        }
        onFinishing();

        if (this.presenter != null) {
            this.presenter.onDestroy();//释放资源
            this.presenter = null;
        }
        this.isCreateed = false;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onDetach()");
        }
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isLogFragmentLife) {
            LogUtils.d("Fragment - " + this.getClass().getSimpleName() + " - onActivityResult(" + requestCode + "," + resultCode + ") data.size = " + ((data == null || data.getExtras() == null) ? 0 : data.getExtras().size()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * RxJava 任务生命周期关联绑定
     */
    @NonNull
    @Override
    public Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
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
    public void onLazyLoadHDB() {

    }

    @Override
    public void onVisiableHDB() {

    }

    @Override
    public void onFinishing() {

    }
}
