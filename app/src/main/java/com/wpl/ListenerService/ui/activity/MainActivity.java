package com.wpl.ListenerService.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.roughike.bottombar.BottomBar;
import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.ui.fragment.HomeFragment;
import com.wpl.ListenerService.ui.fragment.MeFragment;

import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity {
    private boolean isBackPressed;
    private BottomBar bottomBar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initBottomTab();
        BmobUpdateAgent.update(this);
    }

    private void initBottomTab() {
        HomeFragment homeFragment = new HomeFragment();
        MeFragment meFragment = new MeFragment();
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setActiveTabColor(getResources().getColor(R.color.colorAccent));
        bottomBar.useFixedMode();
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, menuItemId -> {
            switch (menuItemId) {
                case R.id.bb_menu_home:
                    replaceFragment(homeFragment);
                    break;
                case R.id.bb_menu_me:
                    replaceFragment(meFragment);
                    break;
                default:
                    break;
            }
        });
        replaceFragment(homeFragment);
        bottomBar.setDefaultTabPosition(0);
        bottomBar.selectTabAtPosition(0, false);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        bottomBar.onSaveInstanceState(outState);
    }

    /**
     * 再按一次退出
     */
    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            System.exit(0);
            return;
        }
        isBackPressed = true;
        ToastShow("再按一次退出");
        new Handler().postDelayed(() -> isBackPressed = false, 2000);
    }
}
