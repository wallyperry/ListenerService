package com.wpl.ListenerService.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * ClientUser
 * Created by 培龙 on 2017/2/21.
 */

public class ClientUser extends BmobObject {
    private String phone;
    private boolean isFeedback;
    private BmobUser belongTo;
    private String phoneInfo;

    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFeedback() {
        return isFeedback;
    }

    public void setFeedback(boolean feedback) {
        isFeedback = feedback;
    }

    public BmobUser getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(BmobUser belongTo) {
        this.belongTo = belongTo;
    }
}
