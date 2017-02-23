package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 *
 * Created by Administrator on 2017/2/23.
 */

public class FDDataDeleteImpl implements M_Presenter.FDDataDeletePresenter {
    private M_View.FDDataDeleteView view;
    private FeedbackData data;

    public FDDataDeleteImpl(M_View.FDDataDeleteView view) {
        this.view = view;
        data = new FeedbackData();
    }

    @Override
    public void deleteFdData(String objId) {
        new Thread(() -> {
            data.setObjectId(objId);
            data.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        view.deleteSuccess();
                    } else {
                        view.deleteError(e);
                    }
                }
            });

        }).start();
    }
}
