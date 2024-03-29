package com.wpl.ListenerService.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.CallLogListViewAdapter;
import com.wpl.ListenerService.base.BaseFragment;
import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.model.AFeedbackDataImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;

import java.util.ArrayList;

import butterknife.Bind;
import cn.bmob.v3.exception.BmobException;

/**
 * CallLogFragment
 * Created by 培龙 on 2017/2/27.
 */
public class CallLogFragment extends BaseFragment implements M_View.AFeedbackData {
    @Bind(R.id.callLog_listView)
    ListView listView;
    @Bind(R.id.callLog_refresh)
    SwipeRefreshLayout refreshLayout;

    private Bundle bundle;
    private CallLogListViewAdapter adapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_call_log;
    }

    @Override
    protected void initView() {
        bundle = activity.getIntent().getExtras();

        initRefresh();

    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(this::getData);
        refreshLayout.post(() -> refreshLayout.setRefreshing(true));
        getData();
    }

    private void getData() {
        M_Presenter.AFeedbackDataPresenter presenter = new AFeedbackDataImpl(this);
        presenter.getAFeedbackData(bundle.getString("objId"));
    }

    @Override
    public void loadSuccess(FeedbackData data) {
        if (isAdded()) {
            refreshLayout.setRefreshing(false);
            ArrayList arrayList = (ArrayList) data.getCallLog();
            adapter = new CallLogListViewAdapter(arrayList, R.layout.item_call_log_list_view, activity);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void loadError(BmobException e) {
        if (isAdded()) {
            refreshLayout.setRefreshing(false);
            ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
        }
    }
}
