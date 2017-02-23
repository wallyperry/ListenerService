package com.wpl.ListenerService.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.HostInfoRecyclerViewAdapter;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.model.ClientListImpl;
import com.wpl.ListenerService.mvp.model.FDDataDeleteImpl;
import com.wpl.ListenerService.mvp.model.FeedbackChangeImpl;
import com.wpl.ListenerService.mvp.model.FeedbackDataImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;
import com.wpl.ListenerService.utils.DialogUtils;
import com.wpl.ListenerService.utils.SPUtils;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLayoutStyle;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLocationStyle;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;


/**
 * 主机信息
 * Created by Administrator on 2017/2/22.
 */
public class HostInfoActivity extends BaseActivity implements M_View.FeedbackDataView, M_View.FeedbackChangeView {
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
        refreshLayout.post(() -> refreshLayout.setRefreshing(true));
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

    @OnClick({R.id.hostInfo_back, R.id.hostInfo_refreshBtn, R.id.hostInfo_fab_1,
            R.id.hostInfo_fab_2, R.id.hostInfo_fab_3, R.id.hostInfo_fab_4, R.id.hostInfo_fab_5})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hostInfo_back:    //back
                finish();
                break;
            case R.id.hostInfo_refreshBtn:  //刷新按钮
                new Handler().post(() -> refreshLayout.setRefreshing(true));
                initData();
                break;
            case R.id.hostInfo_fab_1:   //发送请求
                sendRequest();
                break;
            case R.id.hostInfo_fab_2:   //修改备注
                upUserInfo();
                break;
            case R.id.hostInfo_fab_5:   //直接拨号
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:18244267955")));
                break;
            case R.id.hostInfo_fab_3:   //移除设备
                ToastShow("移除设备");
                break;
            case R.id.hostInfo_fab_4:   //清空记录
                ToastShow("清空记录");
                break;
            default:
                break;
        }
    }

    /**
     * 修改备注
     */
    private void upUserInfo() {
        ToastShow("修改备注");
    }

    /**
     * 发送请求
     */
    private void sendRequest() {
        LemonBubble.showRoundProgress(this, "请求中...");

        //请求网络
        M_Presenter.FeedbackChangePresenter changePresenter = new FeedbackChangeImpl(this);
        changePresenter.changeFeedback(true, objId);
    }

    @Override
    public void loadSuccess(List<FeedbackData> dataList) {
        if (isFinishing()) {
            return;
        }
        new Handler().post(() -> refreshLayout.setRefreshing(false));
        if (dataList.size() == 0) {
            setDataLayout(false);
        } else {
            setDataLayout(true);
            new Handler().post(() -> {
                Collections.sort(dataList, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
                adapter = new HostInfoRecyclerViewAdapter(this, dataList, false);
                adapter.setOnItemClickListener((viewHolder, data, position) -> {
                    ToastShow("地址：" + data.getAddress());
                });
                adapter.setOnItemLongClickListener((viewHolder, data, position) -> {
                    LogE("长按了：" + position + data.getAoi());
                    DialogUtils.showConfirmDialog(HostInfoActivity.this, "提示", "删除这条数据？",
                            "取消", "确定", new DialogUtils.DialogCallBack() {
                                @Override
                                public void onComplete() {
                                    LemonBubble.showRoundProgress(HostInfoActivity.this, "正在删除...");
                                    M_Presenter.FDDataDeletePresenter presenter = new FDDataDeleteImpl(new M_View.FDDataDeleteView() {
                                        @Override
                                        public void deleteSuccess() {
                                            new Handler().postDelayed(() -> {
                                                dataList.remove(viewHolder.getAdapterPosition());
                                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                                adapter.notifyDataSetChanged();
                                                if (dataList.size() == 0) {
                                                    setDataLayout(false);
                                                }
                                                LemonBubble.showRight(HostInfoActivity.this, "删除成功", 1000);
                                            }, 500);
                                        }

                                        @Override
                                        public void deleteError(BmobException e) {
                                            if (!isFinishing()) {
                                                new Handler().postDelayed(() -> LemonBubble.showRight(HostInfoActivity.this, BmobUtils.errorMsg(e.getErrorCode()), 2000), 500);
                                            }
                                        }
                                    });
                                    presenter.deleteFdData(data.getObjectId());
                                }

                                @Override
                                public void onCancel() {
                                }
                            });
                });
                recyclerView.setAdapter(adapter);
            });

        }
    }

    @Override
    public void loadError(BmobException e) {
        if (!isFinishing()) {
            refreshLayout.setRefreshing(false);
            ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
        }
    }

    @Override
    public void changeSuccess() {
        new Handler().postDelayed(() -> LemonBubble.showRight(this, "请求成功", 1000), 500);
    }

    @Override
    public void changeError(BmobException e) {
        if (!isFinishing()) {
            new Handler().postDelayed(() -> LemonBubble.showRight(this, BmobUtils.errorMsg(e.getErrorCode()), 1000), 500);
        }
    }

}
