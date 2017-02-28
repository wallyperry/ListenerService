package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2017/2/24.
 */

public class ChangeUserPassImpl implements M_Presenter.ChangeUserPassPresenter {
    private M_View.ChangeUserPassView view;

    public ChangeUserPassImpl(M_View.ChangeUserPassView view) {
        this.view = view;
    }

    @Override
    public void changePass(String objId, String oldPass, String newPass) {

        new Thread(() -> {
            BmobUser.updateCurrentUserPassword(oldPass, newPass, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        view.changeSuccess();
                    } else {
                        view.changeError(e);
                    }
                }
            });
        }).start();
    }
}
