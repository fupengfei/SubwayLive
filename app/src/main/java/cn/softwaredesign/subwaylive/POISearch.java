package cn.softwaredesign.subwaylive;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

/**
 * Created by Think on 2015/6/1.
 */
public class POISearch implements PoiSearch.OnPoiSearchListener {
    private String address;
    private GeocodeSearch search;
    private String cityName;
    private Context context;
    private LatLonPoint toPoint;
    private RouleMap rouleMap;
    private LatLonPoint fromPoint;
    private AMap aMap;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;// POI搜索
    private String cityCode;
    private View view;

    public POISearch(Context context,String address,String cityName,LatLonPoint fromPoint,AMap amap,String cityCode,View view){
        this.context = context;
        this.address = address;
        this.cityName = cityName;
        this.fromPoint = fromPoint;
        this.aMap = amap;
        this.cityCode = cityCode;
        this.view = view;
        init();
    }

    private void init(){
        query = new PoiSearch.Query(address, "", cityName);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(1);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }





    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    toPoint =  result.getPois().get(0).getLatLonPoint();
                    rouleMap = new RouleMap(context,fromPoint,toPoint,aMap,cityCode,view);
                }
            }
        }
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail poiItemDetail, int i) {

    }
}
