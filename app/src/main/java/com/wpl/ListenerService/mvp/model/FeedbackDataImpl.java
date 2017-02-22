package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * impl
 * Created by Administrator on 2017/2/22.
 */

public class FeedbackDataImpl implements M_Presenter.FeedbackDataPresenter {
    private M_View.FeedbackDataView view;
    private BmobQuery<FeedbackData> query;
    private ClientUser clientUser;

    public FeedbackDataImpl(M_View.FeedbackDataView view) {
        this.view = view;
        query = new BmobQuery<FeedbackData>();
        clientUser = new ClientUser();
    }

    @Override
    public void getFeedbackData(String userId) {
        new Thread(() -> {
            clientUser.setObjectId(userId);
            query.addWhereEqualTo("belongTo", new BmobPointer(clientUser));
            query.findObjects(new FindListener<FeedbackData>() {
                @Override
                public void done(List<FeedbackData> list, BmobException e) {
                    if (e == null) {
                        view.loadSuccess(list);
                    } else {
                        view.loadError(e);
                    }
                }
            });
        }).start();
    }
}
