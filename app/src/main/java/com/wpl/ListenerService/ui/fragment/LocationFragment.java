package com.wpl.ListenerService.ui.fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wpl.ListenerService.R;
import com.wpl.ListenerService.base.BaseFragment;
import com.wpl.ListenerService.mvp.model.CurrentLocationImpl;
import com.wpl.ListenerService.mvp.presenter.M_Presenter;
import com.wpl.ListenerService.mvp.view.M_View;
import com.wpl.ListenerService.ui.activity.LogDetailActivity;
import com.wpl.ListenerService.utils.MyAMapUtil;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 位置
 * Created by 培龙 on 2017/2/27.
 */
public class LocationFragment extends BaseFragment implements M_View.CurrentLocationView {
    @Bind(R.id.location_mapView)
    MapView mapView;
    @Bind(R.id.location_aoi)
    TextView aoiTv;
    @Bind(R.id.location_aoiLL)
    LinearLayout aoiLL;
    @Bind(R.id.location_to)
    RelativeLayout toBtn;
    @Bind(R.id.location_me)
    RelativeLayout meBtn;
    @Bind(R.id.location_floatAction)
    FloatingActionMenu fam;

    private Bundle bundle;
    private AMap aMap;
    private LatLng latLngA;
    private LatLng latLngB;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_location;
    }

    @Override
    protected void initView() {
        bundle = getActivity().getIntent().getExtras();
        aoiLL.getBackground().setAlpha(50);
        initMapView();

    }

    @OnClick({R.id.location_to, R.id.location_me, R.id.location_fab1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_to:
                fam.close(true);
                moveCamera(latLngB);
                break;
            case R.id.location_me:
                fam.close(true);
                moveCamera(latLngA);
                break;
            case R.id.location_fab1:
                if (latLngA!= null || latLngB != null){
                    startToAMap();
                }else {
                    ToastShow("位置都获取失败了，还怎么去找TA？");
                }
                fam.close(true);
                break;
            default:
                break;
        }
    }

    /**
     * 调用高德地图导航
     */
    private void startToAMap() {

        if (MyAMapUtil.isInstallByRead("com.autonavi.minimap")) {
            MyAMapUtil.goToNaviRoute(getContext(), "MyPashServer",
                    String.valueOf(latLngA.latitude), String.valueOf(latLngA.longitude),
                    "", String.valueOf(latLngB.latitude), String.valueOf(latLngB.longitude),
                    "", "0", "0", "0");
        }
    }

    private void initMapView() {
        mapView.onCreate(new LogDetailActivity().getSavedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        initCurrentLocation();
    }

    private void initCurrentLocation() {
        aoiTv.setText("正在获取目标位置...");
        M_Presenter.CurrentLocationPresenter presenter = new CurrentLocationImpl(this);
        presenter.getLocation(getActivity());
    }

    @Override
    public void locationSuccess(Map<String, Object> map) {
        if (map != null) {
            aoiTv.setText(bundle.getString("address"));
            latLngA = new LatLng((double) map.get("lat"), (double) map.get("lon"));
            latLngB = new LatLng(Double.parseDouble(bundle.getString("lat"))
                    , Double.parseDouble(bundle.getString("lon")));
            aMap.addMarker(new MarkerOptions().position(latLngA).title("我")
                    .draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.amap_start))).showInfoWindow();
            aMap.addMarker(new MarkerOptions().position(latLngB).title("TA的位置")
                    .draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.amap_end))).showInfoWindow();
            moveCamera(latLngB);
        }
    }

    private void moveCamera(LatLng latLng) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 16, 0, 0)));
    }

    @Override
    public void locationError(Map<String, Object> map) {
        if (isAdded()) {
            aoiTv.setText("加载位置失败...");
            Toast.makeText(getActivity(), "当前位置获取失败:" + map.get("errorCode"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        mapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
