package com.wpl.ListenerService.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.utils.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的账号
 * Created by Administrator on 2017/2/22.
 */
public class MeInfoActivity extends BaseActivity {
    private SPUtils spUtils;
    @Bind(R.id.meInfo_user)
    TextView userTv;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_me_info;
    }

    @Override
    protected void initView() {
        spUtils = new SPUtils(this, "loginStatus");
        userTv.setText(spUtils.getString("username", ""));
    }

    @OnClick({R.id.meInfo_back, R.id.meInfo_changePass, R.id.meInfo_exitLogin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meInfo_back:  //返回
                finish();
                break;
            case R.id.meInfo_changePass:    //修改密码
                break;
            case R.id.meInfo_exitLogin:     //退出登录
                spUtils.putBoolean("isLogin", false);
                spUtils.putString("objId", "");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
