package com.example.paldo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paldo.APIController.MyAPIController;
import com.example.paldo.APIController.TourAPIController;
import com.example.paldo.Data.TourData;
import com.example.paldo.R;
import com.example.paldo.customClass.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.paldo.Data.Constants.LOCATION_TOUR_CODE;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    ArrayList<TourData> tour_data_list;

    public static ListViewActivity thisActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        thisActivity = this;

        listView = (ListView)findViewById(R.id.listView2);
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TourData item = (TourData)parent.getItemAtPosition(position);

                int contentTypeId = item.getContenttypeid();
                int contentId = item.getContentid();

                String query = "http://localhost:8080";

                query += "/get?contentTypeId=";
                query += contentTypeId;
                query += "?contentId=";
                query += contentId;

                ArrayList<String> URLList = MyAPIController.getMyAPIController().queryAPI(query);

                if(MainActivity.thisActivity != null) {
                    MainActivity.thisActivity.setURLList(URLList);

                    if(SearchActivity.thisActivity != null)
                        SearchActivity.thisActivity.finish();
                }
            }
        });

        Intent intent = getIntent();
        String temp = (String)intent.getStringExtra("TOURDATA");

        tour_data_list = TourAPIController.getToruAPIController().queryAPI
                    (LOCATION_TOUR_CODE, temp);

        if(tour_data_list != null){
            if(tour_data_list.size() == 0){
                Toast toast = Toast.makeText(this, "검색 결과 없음", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(this, "검색결과 : " + tour_data_list.size(), Toast.LENGTH_SHORT);
                toast.show();
                for(int i=0; i<tour_data_list.size(); i++){
                    adapter.addItem(tour_data_list.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
