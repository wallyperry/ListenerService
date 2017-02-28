package com.wpl.ListenerService.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.ui.fragment.CallLogFragment;
import com.wpl.ListenerService.ui.fragment.LocationFragment;
import com.wpl.ListenerService.ui.fragment.SmsLogFragment;
import com.wpl.ListenerService.view.NoScrollViewPager;
import com.wpl.ListenerService.view.TabFragmentIndicator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 详细信息页面
 * Created by 培龙 on 2017/2/27.
 */
public class LogDetailActivity extends BaseActivity {
    @Bind(R.id.logDetail_tabLayout)
    TabFragmentIndicator tabLayout;
    @Bind(R.id.logDetail_viewPager)
    NoScrollViewPager vp;

    private View categoryTab;

    public Bundle getSavedInstanceState;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_log_detail;
    }

    @Override
    protected void initView() {
        this.getSavedInstanceState = savedInstanceState;
        initTabLayout();
    }

    private void initTabLayout() {
        tabLayout.addFragment(0, LocationFragment.class);
        tabLayout.addFragment(1, CallLogFragment.class);
        tabLayout.addFragment(2, SmsLogFragment.class);

        tabLayout.setTabContainerView(R.layout.tab_indicator);
        tabLayout.setTabSliderView(R.layout.tab_slider);
        tabLayout.setOnTabClickListener(v -> {
            if ((Integer) v.getTag() == 0) {
                categoryTab = v;
            }
        });
        vp.setNoScroll(true);
        tabLayout.setViewPager(vp);
    }

    @OnClick({R.id.logDetail_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logDetail_back:   //back
                finish();
                break;
            default:
                break;
        }
    }

}
