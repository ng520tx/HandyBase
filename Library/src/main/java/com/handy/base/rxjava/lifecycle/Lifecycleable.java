package com.handy.base.rxjava.lifecycle;

import android.app.Activity;

import com.trello.rxlifecycle2.RxLifecycle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.reactivex.subjects.Subject;

/**
 * 让 {@link Activity}/{@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 * 无需再继承 {@link RxLifecycle} 提供的 Activity/Fragment ,扩展性极强
 *
 * @author LiuJie https://github.com/Handy045
 * @description Rxjava生命周期管理封装接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
