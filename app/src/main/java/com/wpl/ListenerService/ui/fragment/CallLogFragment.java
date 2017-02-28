package com.wpl.ListenerService.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.CallLogRecyclerViewAdapter;
import com.wpl.ListenerService.base.BaseFragment;
import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.model.AFeedbackDataImpl;
import com.wpl.ListenerService.mvp.model.FeedbackDataImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.exception.BmobException;

/**
 * CallLogFragment
 * Created by 培龙 on 2017/2/27.
 */
public class CallLogFragment extends BaseFragment implements M_View.AFeedbackData {
    @Bind(R.id.callLog_recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.callLog_refresh)
    SwipeRefreshLayout refreshLayout;

    private Bundle bundle;
    private CallLogRecyclerViewAdapter adapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_call_log;
    }

    @Override
    protected void initView() {
        bundle = activity.getIntent().getExtras();
        LogE(bundle.getString("objId"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        initRefresh();

    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(this::initData);
        refreshLayout.post(() -> refreshLayout.setRefreshing(true));
        initData();
    }

    private void initData() {
        M_Presenter.AFeedbackDataPresenter presenter = new AFeedbackDataImpl(this);
        presenter.getAFeedbackData(bundle.getString("objId"));
    }

    @Override
    public void loadSuccess(FeedbackData data) {
        if (isAdded()) {
            LogE("size:" + data.getCallLog().get(0).toString());
        }
    }

    @Override
    public void loadError(BmobException e) {
        if (isAdded()) {
            ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
        }
    }
}
