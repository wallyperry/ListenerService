package com.wpl.ListenerService.ui.activity;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.mvp.model.ChangeUserPassImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;
import com.wpl.ListenerService.utils.SPUtils;
import com.wpl.ListenerService.utils.StringUtils;

import net.lemonsoft.lemonbubble.LemonBubble;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;

/**
 * 修改密码
 * Created by 培龙 on 2017/2/24.
 */
public class ChangePassActivity extends BaseActivity implements M_View.ChangeUserPassView {
    @Bind(R.id.changePass_oldPass)
    EditText inputOldPass;
    @Bind(R.id.changePass_newPass)
    EditText inputNewPass;
    @Bind(R.id.changePass_newPassTry)
    EditText inputNewPassTry;
    private SPUtils spUtils;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected void initView() {
        spUtils = new SPUtils(this, "loginStatus");
    }

    @OnClick({R.id.changePass_back, R.id.changePass_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changePass_back:      //返回
                finish();
                break;
            case R.id.changePass_confirm:   //确认
                upUserPassword();
            default:
                break;
        }
    }

    private void upUserPassword() {
        String inOldPass = inputOldPass.getText().toString().trim();
        String inNewPass = inputNewPass.getText().toString().trim();
        String inNewPassTry = inputNewPassTry.getText().toString().trim();

        if (StringUtils.isEmpty(inOldPass) || StringUtils.isSpace(inOldPass)) {
            ToastShow("请输入旧密码");
        } else if (StringUtils.isEmpty(inNewPass) || StringUtils.isSpace(inNewPass)) {
            ToastShow("请输入新密码");
        } else if (StringUtils.isEmpty(inNewPassTry) || StringUtils.isSpace(inNewPassTry)) {
            ToastShow("请再输入一次新密码");
        } else {
            LemonBubble.showRoundProgress(this, "修改密码...");
            M_Presenter.ChangeUserPassPresenter presenter = new ChangeUserPassImpl(this);
            presenter.changePass(spUtils.getString("objId", ""), inOldPass, inNewPassTry);
        }
    }

    @Override
    public void changeSuccess() {
        LemonBubble.showRight(this, "密码修改成功",1000);
        new Handler().postDelayed(this::finish, 1100);
    }

    @Override
    public void changeError(BmobException e) {
        new Handler().postDelayed(() -> LemonBubble.showError(this, "密码修改失败，请重试", 1000), 500);
    }
}
