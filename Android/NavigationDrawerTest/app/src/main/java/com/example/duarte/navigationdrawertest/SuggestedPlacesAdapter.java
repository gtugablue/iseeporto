package com.example.duarte.navigationdrawertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Duarte on 08/09/2015.
 */
public class SuggestedPlacesAdapter extends BaseAdapter {

    private final Context context;

    public class SuggestedPoiData{
        public int poiID;
        protected String poiName;
        protected String visitorFriends;
        protected int distance;

        SuggestedPoiData(int poiID,String poiName,String visitorFriends,int distance){
            this.poiID = poiID;
            this.poiName = poiName;
            this.visitorFriends = visitorFriends;
            this.distance = distance;
        }

        public int getPoiID() {
            return poiID;
        }

        public String getPoiName() {
            return poiName;
        }

        public String getVisitorFriends() {
            return visitorFriends;
        }

        public int getDistance() {
            return distance;
        }

    }

    private List<SuggestedPoiData> data;


    public SuggestedPlacesAdapter(Context context){
        this.context = context;
        ArrayList<SuggestedPoiData> data;
        data = new ArrayList<>();
        List<String> names =Arrays.asList(context.getResources().getStringArray(R.array.poi_names));
        List<String> visitors = Arrays.asList(context.getResources().getStringArray(R.array.friends_list));
        int[] distances = context.getResources().getIntArray(R.array.distances);

        for(int i = 0; i< distances.length; i++){
            data.add(new SuggestedPoiData(i,names.get(i), visitors.get(i), distances[i]));
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
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getPoiID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row ;
        row = inflater.inflate(R.layout.suggested_pois_row_layout, parent, false);

        ImageView image = (ImageView) row.findViewById(R.id.poi_image);

        TextView name = (TextView) row.findViewById(R.id.feed_text);
        name.setText(data.get(position).getPoiName().toString());

        TextView visitors = (TextView) row.findViewById(R.id.visitors);
        /*if(visitorFriends.get(position).length == 2)
            visitors.setText(visitorFriends.get(position)[0] + " e " + visitorFriends.get(position)[1] + " visitaram este local");
        if(visitorFriends.get(position).length == 1)
            visitors.setText(visitorFriends.get(position)[0] + " visitou este local");
        if(visitorFriends.get(position).length == 0)
            visitors.setText("");*/

        visitors.setText(data.get(position).getVisitorFriends().equals("") ? "" : data.get(position).getVisitorFriends() + " visited this place");

        TextView distance = (TextView) row.findViewById(R.id.distance);
        distance.setText(data.get(position).getDistance() + "m");


        return row;
    }
}
