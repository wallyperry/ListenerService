package com.wpl.ListenerService.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.HostInfoRecyclerViewAdapter;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.model.FeedbackDataImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;


/**
 * 主机信息
 * Created by Administrator on 2017/2/22.
 */
public class HostInfoActivity extends BaseActivity implements M_View.FeedbackDataView {
    @Bind(R.id.hostInfo_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.hostInfo_recycleView)
    RecyclerView recyclerView;
    @Bind(R.id.hostInfo_notData)
    LinearLayout notData;
    @Bind(R.id.hostInfo_title)
    TextView titleTv;

    private String objId;
    private HostInfoRecyclerViewAdapter adapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_host_info;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");
        titleTv.setText(bundle.getString("clientUsername"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(this::initData);
        refreshLayout.setRefreshing(true);
        initData();
    }

    private void initData() {
        M_Presenter.FeedbackDataPresenter presenter = new FeedbackDataImpl(this);
        presenter.getFeedbackData(objId);
    }

    private void setDataLayout(boolean isHave) {
        if (isHave) {
            if (notData.getVisibility() != View.GONE) {
                notData.setVisibility(View.GONE);
            }
            if (recyclerView.getVisibility() != View.VISIBLE) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            if (notData.getVisibility() != View.VISIBLE) {
                notData.setVisibility(View.VISIBLE);
            }
            if (recyclerView.getVisibility() != View.GONE) {
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.hostInfo_back, R.id.hostInfo_refreshBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hostInfo_back:    //back
                finish();
                break;
            case R.id.hostInfo_refreshBtn:  //刷新按钮
                refreshLayout.setRefreshing(true);
                initData();
                break;
            default:
                break;
        }
    }

    @Override
    public void loadSuccess(List<FeedbackData> dataList) {
        refreshLayout.setRefreshing(false);
        if (dataList.size() == 0) {
            setDataLayout(false);
        } else {
            setDataLayout(true);
            Collections.sort(dataList, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
            adapter = new HostInfoRecyclerViewAdapter(this, dataList, false);
            adapter.setOnItemClickListener((viewHolder, data, position) -> {
                ToastShow("电话：" + data.getCallLog().size());
            });
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void loadError(BmobException e) {
        if (!isFinishing()) {
            refreshLayout.setRefreshing(false);
            ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
        }
    }

}
