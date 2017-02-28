package com.wpl.ListenerService.mvp.presenter;

import android.content.Context;

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
     * 获取某一条数据
     */
    interface AFeedbackDataPresenter {
        void getAFeedbackData(String userId);
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

    /**
     * 修改密码
     */
    interface ChangeUserPassPresenter {
        void changePass(String objId, String oldPass, String newPass);
    }

    /**
     * 修改被控端备注
     */
    interface UpClientInfoPresenter {
        void upClientInfo(String objId, String text);
    }

    /**
     * 清空数据
     */
    interface ClearLogDataPresenter {
        void clearLogData(String objId);
    }

    /**
     * 获取当前位置信息
     */
    interface CurrentLocationPresenter{
        void getLocation(Context context);
    }
}
