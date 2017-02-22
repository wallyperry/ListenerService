package com.wpl.ListenerService.ui.activity;

import android.view.View;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;

import butterknife.OnClick;

/**
 * 关于APP
 * Created by Administrator on 2017/2/22.
 */
public class AboutActivity extends BaseActivity {


    @Override
    protected int initLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.about_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_back:   //back
                finish();
                break;
            default:
                break;
        }
    }
}
