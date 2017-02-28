package com.wpl.ListenerService.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.MyFragmentPagerAdapter;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.bean.TabEntity;
import com.wpl.ListenerService.ui.fragment.CallLogFragment;
import com.wpl.ListenerService.ui.fragment.LocationFragment;
import com.wpl.ListenerService.ui.fragment.SmsLogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 详细信息页面
 * Created by 培龙 on 2017/2/27.
 */
public class LogDetailActivity extends BaseActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.logDetail_tabLayout)
    CommonTabLayout tabLayout;
    @Bind(R.id.logDetail_viewPager)
    ViewPager vp;

    private String[] titles = {"位置", "通话", "短信"};
    private List<Fragment> fragments;
    private int[] iconSelectIds = {R.mipmap.ic_tab_me, R.mipmap.ic_tab_me, R.mipmap.ic_tab_me,};
    private int[] iconUnSelectIds = {R.mipmap.ic_tab_me, R.mipmap.ic_tab_me, R.mipmap.ic_tab_me,};
    private MyFragmentPagerAdapter adapter;
    private ArrayList<CustomTabEntity> entities;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_log_detail;
    }

    @Override
    protected void initView() {
        initTabLayout();
    }

    private void initTabLayout() {
        entities = new ArrayList<>();
        fragments = new ArrayList<>();
        fragments.add(new LocationFragment());
        fragments.add(new CallLogFragment());
        fragments.add(new SmsLogFragment());
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        for (int i = 0; i < titles.length; i++) {
            entities.add(new TabEntity(titles[i], iconSelectIds[i], iconUnSelectIds[i]));
        }
        tabLayout.setTabData(entities);
        tabLayout.setOnTabSelectListener(this);
        vp.addOnPageChangeListener(this);
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

    @Override
    public void onTabSelect(int position) {
        vp.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
