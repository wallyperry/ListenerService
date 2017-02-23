package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/2/23.
 */

public class FeedbackChangeImpl implements M_Presenter.FeedbackChangePresenter {
    private M_View.FeedbackChangeView view;
    private ClientUser user;
    private BmobQuery<ClientUser> query;

    public FeedbackChangeImpl(M_View.FeedbackChangeView view) {
        this.view = view;
        user = new ClientUser();
        query = new BmobQuery<ClientUser>();
    }

    @Override
    public void changeFeedback(Boolean isFeedback, String objId) {
        new Thread(() -> {
            query.getObject(objId, new QueryListener<ClientUser>() {
                @Override
                public void done(ClientUser clientUser, BmobException e) {
                    if (e == null) {
                        if (clientUser.isFeedback()) {
                            BmobException exception = new BmobException(-1);
                            view.changeError(exception);
                        } else {
                            changeStatus(clientUser, isFeedback);
                        }
                    } else {
                        view.changeError(e);
                    }
                }
            });
        }).start();
    }

    private void changeStatus(ClientUser clientUser, Boolean isFeedback) {
        user.setFeedback(isFeedback);
        user.update(clientUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    view.changeSuccess();
                } else {
                    view.changeError(e);
                }
            }
        });
    }
}
