package com.wpl.ListenerService.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseFragment;

import butterknife.Bind;

/**
 * 位置
 * Created by 培龙 on 2017/2/27.
 */
public class LocationFragment extends BaseFragment {
    @Bind(R.id.location_text)
    TextView textView;

    private Bundle bundle;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_location;
    }

    @Override
    protected void initView() {
        bundle = activity.getIntent().getExtras();
    }
}
