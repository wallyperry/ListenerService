package com.wpl.ListenerService.mvp.presenter;

/**
 * presenter
 * Created by Administrator on 2017/2/22.
 */

public interface M_Presenter {

    /**
     * 登录
     */
    interface LoginPresenter {
        void login(String username, String password);
    }

    /**
     * 获取被控端列表
     */
    interface ClientListPresenter {
        void getClientList(String userId);
    }

    /**
     * 获取被控端上传的数据
     */
    interface FeedbackDataPresenter {
        void getFeedbackData(String userId);
    }

    /**
     * 更改状态
     */
    interface FeedbackChangePresenter {
        void changeFeedback(Boolean isFeedback, String objId);
    }

    /**
     * 删除返回的数据
     */
    interface FDDataDeletePresenter {
        void deleteFdData(String objId);
    }
}
