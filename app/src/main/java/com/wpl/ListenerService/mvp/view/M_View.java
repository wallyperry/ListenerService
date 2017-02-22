package com.wpl.ListenerService.mvp.view;

import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.bean.FeedbackData;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * view
 * Created by Administrator on 2017/2/22.
 */

public interface M_View {

    /**
     * 登录
     */
    interface LoginView {
        void loginSuccess(BmobUser bmobUser);

        void loginError(BmobException e);
    }

    /**
     * 被控端列表
     */
    interface ClientListView {
        void loadSuccess(List<ClientUser> clientUser);

        void loadError(BmobException e);
    }

    /**
     * 获取被控端上传的数据
     */
    interface FeedbackDataView {
        void loadSuccess(List<FeedbackData> dataList);

        void loadError(BmobException e);
    }
}
