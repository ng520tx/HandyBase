package com.handy.base.app.dagger;

import com.handy.base.mvp.IModel;
import com.handy.base.mvp.IPresenter;
import com.handy.base.mvp.IView;

/**
 * 中文名称
 *
 * @author LiuJie https://github.com/Handy045
 * @description 功能描述
 * @date Created in 2018/2/2 上午1:33
 * @modified By LiuJie
 */
public class UserContract {
    public interface userView extends IView {
    }

    public interface userModel extends IModel {

    }

    public interface userPresenter extends IPresenter {
    }

}
