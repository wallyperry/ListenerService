package com.wpl.ListenerService.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.mvp.model.LoginImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;
import com.wpl.ListenerService.utils.SPUtils;
import com.wpl.ListenerService.utils.StringUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 登录
 * Created by Administrator on 2017/2/22.
 */
public class LoginActivity extends BaseActivity implements M_View.LoginView {
    @Bind(R.id.login_inputUser)
    EditText inputUser;
    @Bind(R.id.login_inputPass)
    EditText inputPass;
    @Bind(R.id.login_pb)
    ProgressBar progressBar;

    private M_Presenter.LoginPresenter loginPresenter;
    private SPUtils spUtils;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        spUtils = new SPUtils(this, "loginStatus");
        loginPresenter = new LoginImpl(this);
    }

    @OnClick({R.id.login_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:    //登录
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        String inUser = inputUser.getText().toString().trim();
        String inPass = inputPass.getText().toString().trim();
        if (StringUtils.isEmpty(inUser) || StringUtils.isSpace(inUser)) {
            ToastShow("账号都没有你登录什么？");
        } else if (StringUtils.isEmpty(inPass) || StringUtils.isSpace(inPass)) {
            ToastShow("密码都没有你登录什么？");
        } else {
            isShowProgress(true);
            loginPresenter.login(inUser, inPass);
        }
    }

    private void isShowProgress(boolean isShow) {
        if (isShow) {
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
        } else {
            if (progressBar.getVisibility() != View.GONE) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void loginSuccess(BmobUser bmobUser) {
        isShowProgress(false);
        spUtils.putBoolean("isLogin", true);
        spUtils.putString("objId", bmobUser.getObjectId());
        spUtils.putString("username", bmobUser.getUsername());
        ToastShow("登录成功");
        new Handler().postDelayed(() -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }, 800);
    }

    @Override
    public void loginError(BmobException e) {
        isShowProgress(false);
        ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
    }

    @Override
    public void onBackPressed() {
    }
}
