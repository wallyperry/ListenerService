package com.wpl.ListenerService.mvp.model;

import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * impl
 * Created by Administrator on 2017/2/22.
 */

public class ClientListImpl implements M_Presenter.ClientListPresenter {
    private M_View.ClientListView view;
    private BmobQuery<ClientUser> query;
    private BmobUser bmobUser;

    public ClientListImpl(M_View.ClientListView view) {
        this.view = view;
        query = new BmobQuery<ClientUser>();
        bmobUser = new BmobUser();
    }

    @Override
    public void getClientList(String userId) {
        new Thread(() -> {
            bmobUser.setObjectId(userId);
            query.addWhereEqualTo("belongTo", new BmobPointer(bmobUser));
            query.findObjects(new FindListener<ClientUser>() {
                @Override
                public void done(List<ClientUser> list, BmobException e) {
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
