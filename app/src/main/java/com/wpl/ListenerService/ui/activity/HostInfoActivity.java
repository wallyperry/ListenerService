package com.wpl.ListenerService.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.HostInfoRecyclerViewAdapter;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.bean.ClientUser;
import com.wpl.ListenerService.bean.FeedbackData;
import com.wpl.ListenerService.mvp.model.ClearLogDataImpl;
import com.wpl.ListenerService.mvp.model.ClientListImpl;
import com.wpl.ListenerService.mvp.model.FDDataDeleteImpl;
import com.wpl.ListenerService.mvp.model.FeedbackChangeImpl;
import com.wpl.ListenerService.mvp.model.FeedbackDataImpl;
import com.wpl.ListenerService.mvp.model.UpClientInfoImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.utils.BmobUtils;
import com.wpl.ListenerService.utils.DialogUtils;
import com.wpl.ListenerService.utils.StringUtils;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;


/**
 * 主机信息
 * Created by Administrator on 2017/2/22.
 */
public class HostInfoActivity extends BaseActivity implements M_View.FeedbackDataView, M_View.FeedbackChangeView, M_View.UpClientInfoView, M_View.ClearLogDataView {
    @Bind(R.id.hostInfo_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.hostInfo_recycleView)
    RecyclerView recyclerView;
    @Bind(R.id.hostInfo_notData)
    LinearLayout notData;
    @Bind(R.id.hostInfo_title)
    TextView titleTv;
    @Bind(R.id.hostInfo_floatAction)
    FloatingActionMenu fam;

    private String objId;
    private String phone;
    private HostInfoRecyclerViewAdapter adapter;
    private String inInfo;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_host_info;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");
        phone = bundle.getString("phone");
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
                fam.close(true);
                finish();
                break;
            case R.id.hostInfo_refreshBtn:  //刷新按钮
                fam.close(true);
                new Handler().post(() -> refreshLayout.setRefreshing(true));
                initData();
                break;
            case R.id.hostInfo_fab_1:   //发送请求
                fam.close(true);
                sendRequest();
                break;
            case R.id.hostInfo_fab_2:   //修改备注
                fam.close(true);
                upUserInfo();
                break;
            case R.id.hostInfo_fab_5:   //直接拨号
                fam.close(true);
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone)));
                break;
            case R.id.hostInfo_fab_3:   //移除设备
                fam.close(true);
                ToastShow("移除设备");
                break;
            case R.id.hostInfo_fab_4:   //清空记录
                fam.close(true);
                clearLog();
                break;
            default:
                fam.close(true);
                break;
        }
    }

    /**
     * 清空数据
     */
    private void clearLog() {
        LemonBubble.showRoundProgress(this, "正在清空所有记录...");
        M_Presenter.ClearLogDataPresenter presenter = new ClearLogDataImpl(this);
        presenter.clearLogData(objId);
    }

    /**
     * 修改备注
     */
    private void upUserInfo() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_input_info,
                (ViewGroup) findViewById(R.id.inputInfo_dialog));
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(layout).create();
        alertDialog.show();
        EditText infoEt = (EditText) layout.findViewById(R.id.inputInfo_et);
        layout.findViewById(R.id.inputInfo_btn).setOnClickListener(v -> {
            inInfo = infoEt.getText().toString().trim();
            if (StringUtils.isEmpty(inInfo) || StringUtils.isSpace(inInfo)) {
                ToastShow("备注都没写你改什么？");
            } else {
                M_Presenter.UpClientInfoPresenter presenter = new UpClientInfoImpl(this);
                presenter.upClientInfo(objId, infoEt.getText().toString().trim());
                alertDialog.dismiss();
            }
        });

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
            Collections.sort(dataList, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
            adapter = new HostInfoRecyclerViewAdapter(this, dataList, true);
            adapter.setOnItemClickListener((viewHolder, data, position) -> {
                fam.close(true);
                Bundle bundle = new Bundle();
                bundle.putString("objId", data.getObjectId());
                bundle.putString("lat", data.getLat());
                bundle.putString("lon", data.getLon());
                bundle.putString("aoi", data.getAoi());
                bundle.putString("address", data.getAddress());
                bundle.putString("city", data.getCity());
                Intent intent = new Intent(HostInfoActivity.this, LogDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
            adapter.setOnItemLongClickListener((viewHolder, data, position) -> {
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

    @Override
    public void onSuccess() {
        titleTv.setText(inInfo);
    }

    @Override
    public void onError(BmobException e) {
        if (!isFinishing()) {
            ToastShow(BmobUtils.errorMsg(e.getErrorCode()));
        }
    }

    @Override
    public void clearSuccess() {
        initRefresh();
        new Handler().postDelayed(() -> LemonBubble.showRight(this, "已清空", 1000), 500);
    }

    @Override
    public void clearError(BmobException e) {
        if (!isFinishing()) {
            new Handler().postDelayed(() -> LemonBubble.showRight(this, BmobUtils.errorMsg(e.getErrorCode()), 1000), 500);
        }
    }
}
