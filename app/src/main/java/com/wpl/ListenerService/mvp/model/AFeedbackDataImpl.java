package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 培龙 on 2017/2/28.
 */

public class AFeedbackDataImpl implements M_Presenter.AFeedbackDataPresenter {
    private M_View.AFeedbackData view;
    private BmobQuery<FeedbackData> query;

    public AFeedbackDataImpl(M_View.AFeedbackData view) {
        this.view = view;
        query = new BmobQuery<>();
    }

    @Override
    public void getAFeedbackData(String userId) {
        new Thread(() -> {
            query.getObject(userId, new QueryListener<FeedbackData>() {
                @Override
                public void done(FeedbackData data, BmobException e) {
                    if (e == null) {
                        view.loadSuccess(data);
                    } else {
                        view.loadError(e);
                    }
                }
            });
        }).start();
    }
}
