package com.wpl.ListenerService.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseActivity;
import com.wpl.ListenerService.utils.DialogUtils;
import com.wpl.ListenerService.utils.PermissionsChecker;
import com.wpl.ListenerService.utils.SPUtils;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * 启动页面
 * Created by Administrator on 2017/2/22.
 */

public class StartActivity extends BaseActivity implements DialogUtils.DialogCallBack {

    private SPUtils spUtils;
    private boolean isRequireCheck; //是否需要系统权限检测
    static final String[] PERMISSIONS = new String[]{   //危险权限（运行时权限）
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE
    };
    private PermissionsChecker mPermissionsChecker; //检查权限
    private static final int PERMISSION_REQUEST_CODE = 0;   //系统权限返回码
    private static final String PACKAGE_URL_SCHEME = "package:";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        spUtils = new SPUtils(this, "loginStatus");
        initPermissions();
    }

    private void initPermissions() {
        mPermissionsChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void checkPermissions() {
        if (isRequireCheck) {//权限没有授权，进入授权界面
            if (mPermissionsChecker.judgePermissions(PERMISSIONS)) {
                LogE("缺少权限");
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
            } else {
                LogE("权限通过");
                DialogUtils.showConfirmDialog(this, getResources().getString(R.string.disclaimer)
                        , getResources().getString(R.string.agreement), "退出", "同意", this);

            }
        } else {
            isRequireCheck = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
        } else {
            isRequireCheck = false;
            showPermissionDialog();
        }
    }

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", (dialog, which) -> finish());

        builder.setPositiveButton("设置", (dialog, which) -> startAppSettings());
        builder.setCancelable(false);
        builder.show();
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onComplete() {
        initUser();
    }

    @Override
    public void onCancel() {
        finish();
        System.exit(0);
    }

    private void initUser() {
        if (spUtils.getBoolean("isLogin", false)) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }, 1200);
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }, 1200);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
