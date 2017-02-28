package com.wpl.ListenerService.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.ClientUserRecyclerViewAdapter;
import com.wpl.ListenerService.base.BaseFragment;
import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.mvp.model.ClientListImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.ui.activity.HostInfoActivity;
import com.wpl.ListenerService.utils.BmobUtils;
import com.wpl.ListenerService.utils.SPUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;

/**
 * 首页
 * Created by Administrator on 2017/2/22.
 */
public class HomeFragment extends BaseFragment implements M_View.ClientListView {
    @Bind(R.id.home_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.home_recycleView)
    RecyclerView recyclerView;
    @Bind(R.id.home_notData)
    LinearLayout notData;

    private SPUtils spUtils;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        spUtils = new SPUtils(activity, "loginStatus");
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(this::initData);
        refreshLayout.setRefreshing(true);
        initData();
    }

    private void initData() {
        M_Presenter.ClientListPresenter presenter = new ClientListImpl(this);
        presenter.getClientList(spUtils.getString("objId", ""));
    }

    @OnClick({R.id.home_refreshBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_refreshBtn:     //刷新按钮
                refreshLayout.setRefreshing(true);
                initData();
                break;
        }
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

    @Override
    public void loadSuccess(List<ClientUser> clientUser) {
        refreshLayout.setRefreshing(false);
        if (clientUser.size() == 0) {
            setDataLayout(false);
        } else {
            setDataLayout(true);
            ClientUserRecyclerViewAdapter adapter = new ClientUserRecyclerViewAdapter(activity, clientUser, false);
            adapter.setOnItemClickListener((viewHolder, data, position) -> {
                Bundle bundle = new Bundle();
                bundle.putString("objId", data.getObjectId());
                bundle.putString("phone", data.getPhone());
                bundle.putString("clientUsername", data.getPhoneInfo());
                Intent intent = new Intent(activity, HostInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void loadError(BmobException e) {
        if (isAdded()) {
            refreshLayout.setRefreshing(false);
            ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initRefresh();
    }
}
