package cn.softwaredesign.subwaylive;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

/**
 * Created by Think on 2015/5/30.
 */
public class PositionActivity
        extends Activity
        implements LocationSource,AMapLocationListener{

    private MapView basicMap;
    private AMap amap;
    private OnLocationChangedListener mLocation;
    private LocationManagerProxy mLocationProxy;
    private String cityCode = "021";
    private String cityName = "上海市";
    private LatLonPoint fromPoint;
    private TipsLocation tipsLocation;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.position_activity);
        // 初始化地图容器
        basicMap = (MapView)findViewById(R.id.basicMap);
        //创建容器
        basicMap.onCreate(bundle);
        init();
       // CameraUpdateFactory.changeBearing(90);
    }
    private void init(){
        if(amap == null){
            amap = basicMap.getMap();
            setUpMap();
        }
    }

    private void setUpMap(){
       // amap.setPointToCenter(100,0);
       //  amap.setMapTextZIndex(2);
        amap.setLocationSource(this); // 设置定位监听器
        amap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        amap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        basicMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        basicMap.onPause();
        deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basicMap.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        basicMap.onSaveInstanceState(outState);
    }

    /**
     * 激活定位
     * @param mLocation
     */
    @Override
    public void activate(OnLocationChangedListener mLocation) {
        this.mLocation = mLocation;
        if(mLocationProxy == null){
            mLocationProxy = LocationManagerProxy.getInstance(this);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用destroy()方法
            // 其中如果间隔时间为-1，则定位只定一次
            // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
            mLocationProxy.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mLocation = null;
        if (mLocationProxy != null) {
            mLocationProxy.removeUpdates(this);
            mLocationProxy.destroy();
        }
        mLocationProxy = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mLocation != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getAMapException().getErrorCode() == 0) {
                mLocation.onLocationChanged(amapLocation);// 显示系统小蓝点
                fromPoint = new LatLonPoint(amapLocation.getLatitude(),amapLocation.getLongitude());
                cityCode = amapLocation.getCityCode();
                cityName = amapLocation.getCity();
                new TipsLocation(this,amapLocation.getPoiName(),cityName,fromPoint,amap,cityCode,this.findViewById(R.id.btnCar));
            } else {
                Log.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
            }
        }
    }

    /**
     * 此方法已经废弃
     */
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
