package com.handy.base.rxjava.lifecycle;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

import androidx.fragment.app.Fragment;

/**
 * 让 {@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 *
 * @author LiuJie https://github.com/Handy045
 * @description Rxjava生命周期管理封装接口
 * @date Created in 2018/2/1 上午11:16
 * @modified By LiuJie
 */
public interface FragmentLifecycleable extends Lifecycleable<FragmentEvent> {
}
