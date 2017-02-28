package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * ClearLogDataImpl
 * Created by 培龙 on 2017/2/24.
 */

public class ClearLogDataImpl implements M_Presenter.ClearLogDataPresenter {
    private M_View.ClearLogDataView view;
    private BmobQuery<FeedbackData> query;

    public ClearLogDataImpl(M_View.ClearLogDataView view) {
        this.view = view;
        query = new BmobQuery<>();
    }

    @Override
    public void clearLogData(String objId) {
        new Thread(() -> {
            query.addQueryKeys("objectId");
            query.findObjects(new FindListener<FeedbackData>() {
                @Override
                public void done(List<FeedbackData> list, BmobException e) {
                    if (e == null) {
                        if (list.size() > 0) {
                            forEach(list);
                        } else {
                            view.clearError(new BmobException(-2));
                        }
                    } else {
                        view.clearError(e);
                    }
                }
            });
        }).start();
    }

    private void forEach(List<FeedbackData> list) {
        for (FeedbackData data : list) {
            deleteData(data.getObjectId());
        }
    }

    private void deleteData(String objId) {
        FeedbackData data = new FeedbackData();
        data.setObjectId(objId);
        data.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    view.clearSuccess();
                } else {
                    view.clearError(e);
                }
            }
        });
    }
}
