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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Duarte on 08/09/2015.
 */
public class SuggestedPlacesAdapter extends BaseAdapter {

    private final Context context;
    private List<String> poiName;

    private List<String> visitorFriends;

    private int[] distanceArray;

    public SuggestedPlacesAdapter(Context context){
        Log.i("Começa a criar adapter", "Init");
        this.context = context;
        poiName =Arrays.asList(context.getResources().getStringArray(R.array.suggested_pois));
        for(String name : poiName)
            Log.i("name:" , name);
        visitorFriends = Arrays.asList(context.getResources().getStringArray(R.array.suggested_visitors_friends));
        for(String name : visitorFriends)
            Log.i("friend:" , name);
        distanceArray = context.getResources().getIntArray(R.array.distances);
        for(Integer i : distanceArray)
            Log.i("dist:" , i.toString());
        Log.i("Criou adapter", "Suc");
    }

    @Override
    public int getCount() {
        Log.i("executado:" , "getCount");
        return 1;
    }

    @Override
    public Object getItem(int position) {
        Log.i("executado:" , "getItem " + position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        Log.i("executado:" , "getItemId " +position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("getView", "Init");
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row ;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.suggested_pois_row_layout, parent, false);
            row = new View(this.context);
            convertView.setTag(row);
            Log.i("Caso", "a");
        } else {
            row = (View) convertView.getTag();
            Log.i("Caso", "b");
        }

        ImageView image = (ImageView) row.findViewById(R.id.poi_image);

        TextView name = (TextView) row.findViewById(R.id.poi_name);
        name.setText(poiName.get(position));
//        name.setText("Clerigos");

        TextView visitors = (TextView) row.findViewById(R.id.visitors);
        /*if(visitorFriends.get(position).length == 2)
            visitors.setText(visitorFriends.get(position)[0] + " e " + visitorFriends.get(position)[1] + " visitaram este local");
        if(visitorFriends.get(position).length == 1)
            visitors.setText(visitorFriends.get(position)[0] + " visitou este local");
        if(visitorFriends.get(position).length == 0)
            visitors.setText("");*/

        visitors.setText(visitorFriends.get(position) + " visitou este local");
//        visitors.setText("José" + "visitou este local");

        TextView distance = (TextView) row.findViewById(R.id.distance);
        distance.setText(distanceArray[position] + "m");
//        distance.setText("200" + "m");
        Log.i("retorna a row", "Suc");

        return row;
    }
}
