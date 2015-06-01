package cn.softwaredesign.subwaylive;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

/**
 * Created by Think on 2015/5/31.
 */
public class RouleMap  implements RouteSearch.OnRouteSearchListener,View.OnClickListener {
    private LatLonPoint fromPoint;
    private LatLonPoint toPoint;
    private Context context;
    private AMap amap;
    private RouteSearch route;
    private String cityCode;
    private Button btnWalk, btnBus, btnCar;
    private View view;
    private LayoutInflater inflater;

    public RouleMap(Context context, LatLonPoint fromPoint, LatLonPoint toPoint, AMap amap,String cityCode,View view) {
        this.context = context;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.amap = amap;
        this.cityCode = cityCode;
        //this.view = view;
        init();
    }

    public void init() {
        initView();
        route = new RouteSearch(context);
        route.setRouteSearchListener(this);
        route.calculateWalkRouteAsyn(new RouteSearch.WalkRouteQuery(new RouteSearch.FromAndTo(fromPoint, toPoint), RouteSearch.BusDefault));
    }


    public void initView(){
        if(view == null){
            inflater = LayoutInflater.from(context);
            view = inflater.inflate (R.layout.position_activity, null);
            btnWalk = (Button) view.findViewById(R.id.btnWalk);
            btnBus = (Button) view.findViewById(R.id.btnBus);
            btnCar = (Button) view.findViewById(R.id.btnCar);
            btnWalk.setOnClickListener(this);
            btnBus.setOnClickListener(this);
            btnCar.setOnClickListener(this);
        }
//        ((Button)view).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWalk:
                route.calculateWalkRouteAsyn(new RouteSearch.WalkRouteQuery(new RouteSearch.FromAndTo(fromPoint, toPoint), RouteSearch.BusDefault));
                break;
            case R.id.btnBus:
                route.calculateBusRouteAsyn(new RouteSearch.BusRouteQuery(new RouteSearch.FromAndTo(fromPoint, toPoint), RouteSearch.BusLeaseWalk, cityCode, 0));
                break;
            case R.id.btnCar:
                route.calculateDriveRouteAsyn(new RouteSearch.DriveRouteQuery(new RouteSearch.FromAndTo(fromPoint, toPoint), RouteSearch.DrivingMultiStrategy, null, null, ""));
                break;
        }
    }
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                WalkPath walkPath = result.getPaths().get(0);
                amap.clear(); // 清理之前的图标
                WalkRouteOverlay overlay = new WalkRouteOverlay(context, amap, walkPath, result.getStartPos(), result.getTargetPos());
                overlay.removeFromMap();
                overlay.addToMap();
                overlay.zoomToSpan();
            } else
                Toast.makeText(context, "集合为空", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "返回状态：" + rCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                BusRouteResult busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);
                amap.clear();//清理之前的图标
                BusRouteOverlay routeOverlay = new BusRouteOverlay(
                        context, amap, busPath, busRouteResult.getStartPos(),
                        busRouteResult.getTargetPos());
                routeOverlay.removeFromMap();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            }
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                DrivePath drivePath = result.getPaths().get(0);
                amap.clear();//清理之前的图标 
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(context, amap, drivePath, result.getStartPos(), result.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            }
        }
    }

}
