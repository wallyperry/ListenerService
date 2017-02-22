package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录
 * Created by Administrator on 2017/2/22.
 */

public class LoginImpl implements M_Presenter.LoginPresenter {
    private M_View.LoginView view;
    private BmobUser bmobUser;

    public LoginImpl(M_View.LoginView view) {
        this.view = view;
        bmobUser = new BmobUser();
    }

    @Override
    public void login(String username, String password) {
        new Thread(() -> {
            bmobUser.setUsername(username);
            bmobUser.setPassword(password);
            bmobUser.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
                        view.loginSuccess(bmobUser);
                    } else {
                        view.loginError(e);
                    }
                }
            });
        }).start();
    }
}
