package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * UpClientInfoImpl
 * Created by 培龙 on 2017/2/24.
 */

public class UpClientInfoImpl implements M_Presenter.UpClientInfoPresenter {
    private M_View.UpClientInfoView view;
    private ClientUser user;

    public UpClientInfoImpl(M_View.UpClientInfoView view) {
        this.view = view;
        user = new ClientUser();
    }

    @Override
    public void upClientInfo(String objId, String text) {
        new Thread(() -> {
            user.setPhoneInfo(text);
            user.update(objId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        view.onSuccess();
                    }else {
                        view.onError(e);
                    }
                }
            });
        }).start();
    }
}
