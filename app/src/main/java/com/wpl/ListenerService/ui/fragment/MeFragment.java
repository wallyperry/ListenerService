package com.wpl.ListenerService.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseFragment;
import com.wpl.ListenerService.ui.activity.AboutActivity;
import com.wpl.ListenerService.ui.activity.MeInfoActivity;
import com.wpl.ListenerService.utils.DialogUtils;

import butterknife.OnClick;

/**
 * 我的
 * Created by Administrator on 2017/2/22.
 */
public class MeFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.me_meInfo, R.id.me_meAbout, R.id.me_servicePhone})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_meInfo:    //我的账号
                startActivity(new Intent(activity, MeInfoActivity.class));
                break;
            case R.id.me_meAbout:   //关于软件
                startActivity(new Intent(activity, AboutActivity.class));
                break;
            case R.id.me_servicePhone:  //客服电话
                callServicePhone();
                break;
            default:
                break;
        }
    }

    private void callServicePhone() {
        DialogUtils.showConfirmDialog(getActivity(), "", "18244267955", "取消", "呼叫",
                new DialogUtils.DialogCallBack() {
                    @Override
                    public void onComplete() {
                        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:18244267955")));
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

}
