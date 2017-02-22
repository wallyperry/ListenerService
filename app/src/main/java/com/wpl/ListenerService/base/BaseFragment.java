package com.wpl.ListenerService.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 *
 * Created by 培龙 on 2017/2/13.
 */

public abstract class BaseFragment extends Fragment {

    protected Toast toast;

    //宿主Activity
    protected FragmentActivity activity;

    //根View
    protected View rootView;

    //是否对用户可见
    protected boolean isVisible;

    //是否加载完成
    //当执行完onCreateView，view的初始化方法后即为true
    protected boolean isPrepare;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutResourceId(), container, false);
        ButterKnife.bind(this, rootView);
        initData(getArguments());
        initView();
        isPrepare = true;
        onLazyLoad();
        setListener();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser() {
        if (isPrepare && isVisible) {
            onLazyLoad();
        }
    }

    /**
     * 懒加载，仅当前用户可见View初始化结束后才会执行
     */
    protected void onLazyLoad() {

    }

    /**
     * 设置监听事件
     */
    protected void setListener() {

    }

    /**
     * 设置跟布局的资源ID
     *
     * @return layoutId
     */
    protected abstract int setLayoutResourceId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     *
     * @param arguments 接受到的从其他地方传过来的参数
     */
    protected void initData(Bundle arguments) {

    }

    /**
     * 防止Toast重复显示
     *
     * @param msg 消息
     */
    public void ToastShow(String msg) {
        if (toast == null) {
            toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void LogE(String s) {
        Log.e(getClass().getName(), s);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
