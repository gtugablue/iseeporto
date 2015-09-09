package com.example.duarte.navigationdrawertest;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
import android.util.Log;
=======
import android.content.Context;
>>>>>>> Duarte
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

<<<<<<< HEAD
import org.w3c.dom.Text;

=======
>>>>>>> Duarte
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Duarte on 08/09/2015.
 */
public class SuggestedPlacesAdapter extends BaseAdapter {

    private final Context context;
<<<<<<< HEAD
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
=======

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
>>>>>>> Duarte
    }

    @Override
    public int getCount() {
<<<<<<< HEAD
        Log.i("executado:" , "getCount");
        return poiName.size();
=======
        return data.size();
>>>>>>> Duarte
    }

    @Override
    public Object getItem(int position) {
<<<<<<< HEAD
        Log.i("executado:" , "getItem " + position);
        return null;
=======
        return data.get(position);
>>>>>>> Duarte
    }

    @Override
    public long getItemId(int position) {
<<<<<<< HEAD
        Log.i("executado:" , "getItemId " +position);
        return 0;
=======
        return data.get(position).getPoiID();
>>>>>>> Duarte
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
<<<<<<< HEAD
        Log.i("getView", "Init");
=======
>>>>>>> Duarte
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row ;
        row = inflater.inflate(R.layout.suggested_pois_row_layout, parent, false);

<<<<<<< HEAD

        /*if (convertView == null) {
            convertView.setTag(row);
            Log.i("Caso", "a");
        } else {
            row = (View) convertView.getTag();
            Log.i("Caso", "b");
        }*/
        ImageView image = (ImageView) row.findViewById(R.id.poi_image);

        TextView name = (TextView) row.findViewById(R.id.poi_name);
        Log.i("TextView name null", (name == null) + "");
        name.setText(poiName.get(position));
//        name.setText("Clerigos");
=======
        ImageView image = (ImageView) row.findViewById(R.id.poi_image);

        TextView name = (TextView) row.findViewById(R.id.feed_text);
        name.setText(data.get(position).getPoiName().toString());
>>>>>>> Duarte

        TextView visitors = (TextView) row.findViewById(R.id.visitors);
        /*if(visitorFriends.get(position).length == 2)
            visitors.setText(visitorFriends.get(position)[0] + " e " + visitorFriends.get(position)[1] + " visitaram este local");
        if(visitorFriends.get(position).length == 1)
            visitors.setText(visitorFriends.get(position)[0] + " visitou este local");
        if(visitorFriends.get(position).length == 0)
            visitors.setText("");*/

<<<<<<< HEAD
        visitors.setText(visitorFriends.get(position) + " visitou este local");
//        visitors.setText("José" + "visitou este local");

        TextView distance = (TextView) row.findViewById(R.id.distance);
        distance.setText(distanceArray[position] + "m");
//        distance.setText("200" + "m");
        Log.i("retorna a row", "Suc");
=======
        visitors.setText(data.get(position).getVisitorFriends().equals("") ? "" : data.get(position).getVisitorFriends() + " visited this place");

        TextView distance = (TextView) row.findViewById(R.id.distance);
        distance.setText(data.get(position).getDistance() + "m");

>>>>>>> Duarte

        return row;
    }
}
