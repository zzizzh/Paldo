package com.example.paldo.NaverAPI;

/**
 * Created by jm on 2017-04-24.
 */
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.paldo.Activity.MainActivity;
import com.example.paldo.Data.TourData;
import com.example.paldo.R;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.maplib.NGeoPoint;

import java.util.ArrayList;

public class MarkerController {

    private ArrayList<NMapOverlayItem> marker_list;
    private static Drawable marker = null;

    public MarkerController(Drawable marker){
        marker_list = new ArrayList<NMapOverlayItem>();
        this.marker = marker;
    }

    public void addMarker(ArrayList<TourData> tour_data_list){
        for(int i=0; i<tour_data_list.size(); i++) {
            NGeoPoint point = new NGeoPoint(tour_data_list.get(i).getMapx(), tour_data_list.get(i).getMapy());
            NMapOverlayItem item = new NMapOverlayItem(point, tour_data_list.get(i).getTitle(), tour_data_list.get(i).getAdd1(), marker);
            marker_list.add(item);
        }
    }

    public void clearMarker(){
       marker_list.clear();
    }

    public void setVisibility(int visivility){
        for(int i=0; i<marker_list.size(); i++)
            marker_list.get(i).setVisibility(visivility);
        Log.d("MARKER", "visible");
    }

    public int getListSize(){
        return marker_list.size();
    }
}
