package com.handy.base.app.dagger;

import com.blankj.utilcode.util.LogUtils;
import com.handy.base.mvp.BaseModel;

import javax.inject.Inject;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/2 下午4:43
 * @modified By LiuJie
 */
public class UserModel extends BaseModel implements UserContract.userModel {
    private UserContract.userView view;

    @Inject
    public UserModel(UserContract.userView view) {
        this.view = view;
        LogUtils.d("UserModel is Created");
    }

    @Override
    public void onDestroy() {

    }
}
