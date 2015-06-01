package cn.softwaredesign.subwaylive;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * Created by Think on 2015/5/31.
 * 描述：用来查询坐标
 *
 * 该类已关闭
 */
public class SearchGeocoder implements GeocodeSearch.OnGeocodeSearchListener{
    private String address;
    private GeocodeSearch search;
    private String cityCode;
    private Context context;
    private LatLonPoint toPoint;
    private RouleMap rouleMap;
    private LatLonPoint fromPoint;
    private AMap aMap;
    public SearchGeocoder(Context context,String address,String cityCode,LatLonPoint fromPoint,AMap amap){
        this.context = context;
        this.address = address;
        this.cityCode = cityCode;
        this.fromPoint = fromPoint;
        this.aMap = amap;
        init();
    }

    private void init(){
        search  = new GeocodeSearch(context);
        search.setOnGeocodeSearchListener(this);
        search.getFromLocationNameAsyn(new GeocodeQuery(address,cityCode));
    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if(rCode == 0){
            if(result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0 ){
              //  GeocodeQuery query = result.getGeocodeQuery();
               // System.out.println(result.getGeocodeAddressList().get(0).getDistrict());
                GeocodeAddress ads = result.getGeocodeAddressList().get(0);
                toPoint = ads.getLatLonPoint();// 目的地坐标
               // toPoint = new LatLonPoint(31.337522,121.49774);
                String desc = ads.getFormatAddress();
               // rouleMap = new RouleMap(context,fromPoint,toPoint,aMap);
            }
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {}
}
