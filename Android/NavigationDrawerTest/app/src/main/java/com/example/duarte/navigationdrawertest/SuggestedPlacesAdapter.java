package com.example.duarte.navigationdrawertest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Duarte on 08/09/2015.
 */
public class SuggestedPlacesAdapter extends BaseAdapter {

    private final Context context;

    public class SuggestedPoiData{
        protected String poiName;
        protected String visitorFriends;
        protected int distance;

        SuggestedPoiData(String poiName,String visitorFriends,int distance){
            this.poiName = poiName;
            this.visitorFriends = visitorFriends;
            this.distance = distance;
        }
    }

    private List<SuggestedPoiData> data;


    public SuggestedPlacesAdapter(Context context){
        this.context = context;
        ArrayList<SuggestedPoiData> data = new ArrayList<SuggestedPoiData>();
        List<String> names =Arrays.asList(context.getResources().getStringArray(R.array.suggested_pois));
        List<String> visitors = Arrays.asList(context.getResources().getStringArray(R.array.suggested_visitors_friends));
        int[] distances = context.getResources().getIntArray(R.array.distances);

        for(int i = 0; i< distances.length; i++){
            data.add(new SuggestedPoiData(names.get(i), visitors.get(i), distances[i]));
        }

        this.data = data;
    }

    public SuggestedPlacesAdapter(Context context, ArrayList<SuggestedPoiData> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row ;
        row = inflater.inflate(R.layout.suggested_pois_row_layout, parent, false);

        ImageView image = (ImageView) row.findViewById(R.id.poi_image);

        TextView name = (TextView) row.findViewById(R.id.poi_name);
        name.setText(data.get(position).poiName.toString());

        TextView visitors = (TextView) row.findViewById(R.id.visitors);
        /*if(visitorFriends.get(position).length == 2)
            visitors.setText(visitorFriends.get(position)[0] + " e " + visitorFriends.get(position)[1] + " visitaram este local");
        if(visitorFriends.get(position).length == 1)
            visitors.setText(visitorFriends.get(position)[0] + " visitou este local");
        if(visitorFriends.get(position).length == 0)
            visitors.setText("");*/

        visitors.setText(data.get(position).visitorFriends + " visitou este local");

        TextView distance = (TextView) row.findViewById(R.id.distance);
        distance.setText(data.get(position).distance + "m");


        return row;
    }
}
