package cn.softwaredesign.subwaylive;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;

import java.util.List;

/**
 * Created by Think on 2015/5/31.
 * 用来查询最近的地铁站
 */
public class TipsLocation implements Inputtips.InputtipsListener{
    private Context context;
    private String tipName;
    private String poiName;
    private String cityName;
    private String cityCode;
    private POISearch poiSearch;
    private AMap amap;
    private LatLonPoint fromPoint;
    private View view;
    public TipsLocation(Context context,String poiName, String cityName,LatLonPoint fromPoint,AMap amap,String cityCode,View view){
        this.context = context;
        this.poiName = poiName;
        this.cityName = cityName;
        this.amap = amap;
        this.fromPoint = fromPoint;
        this.cityCode = cityCode;
        this.view = view;
        init();
    }

    private void init(){
        try{
            Inputtips tips = new Inputtips(context,this);
            tips.requestInputtips(poiName+"最近的地铁站",cityName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onGetInputtips(List<Tip> list, int rCode) {
        if(rCode == 0){
            tipName = list.get(0).getName();
            //searchGeocoder = new SearchGeocoder(context,tipName,cityName,fromPoint,amap);
            poiSearch = new POISearch(context,tipName,cityName,fromPoint,amap,cityCode,view);
        }else
            Toast.makeText(context,rCode,Toast.LENGTH_SHORT).show();
    }
}
