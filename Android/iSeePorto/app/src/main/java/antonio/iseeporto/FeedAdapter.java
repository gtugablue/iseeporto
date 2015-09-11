package antonio.iseeporto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Duarte on 09/09/2015.
 */
public class FeedAdapter extends BaseAdapter {


    private final Context context;
    private List<FeedData> data;

    public class FeedData{

        protected int poiImage1;
        protected int poiImage2;
        protected String poiName1;
        protected String poiName2;
        protected int friendID;
        protected int friendImage;
        protected String friendName;
        protected Date timestamp;

        FeedData(int poiImage1, int poiImage2, String poiName1, String poiName2,int friendID, int friendImage, String friendName, Date timestamp){
            this.poiImage1 = poiImage1;
            this.poiImage2 = poiImage2;
            this.poiName1 = poiName1;
            this.poiName2 = poiName2;
            this.friendID = friendID;
            this.friendImage = friendImage;
            this.friendName = friendName;
            this.timestamp = timestamp;
        }

        public int getPoiImage1() {
            return poiImage1;
        }

        public int getPoiImage2() {
            return poiImage2;
        }

        public String getPoiName1() {
            return poiName1;
        }

        public String getPoiName2() {
            return poiName2;
        }

        public int getFriendID() {
            return friendID;
        }

        public int getFriendImage() {
            return friendImage;
        }

        public String getFriendName() {
            return friendName;
        }

        public Date getTimestamp() {
            return timestamp;
        }
    }


    FeedAdapter(Context context){
        this.context = context;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        List<FeedData> data = new ArrayList<>();
        List<String> friends = Arrays.asList(context.getResources().getStringArray(R.array.friends_list));
        List<String> dates = Arrays.asList(context.getResources().getStringArray(R.array.visited_dates));

        for(int i = 0; i < friends.size(); i++){
            try {
                Date date = format.parse(dates.get(i));
                data.add(new FeedData(0,0,"Clérigos", "Ribeira",i,0, friends.get(i),date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        this.data = data;
    }

    FeedAdapter(Context context, ArrayList<FeedData> data){
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row ;
        row = inflater.inflate(R.layout.feed_row_layout, parent, false);
        FeedData dataPosition = data.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.friend_user_image);



        TextView feedText = (TextView) row.findViewById(R.id.achievement_name);
//        String text = "<b>"+ dataPosition.getFriendName() + "</b>" + " visited" + "<b>" + dataPosition.getPoiName1()+ "</b>" + " and " + "<b>" + dataPosition.getPoiName2()+ "</b>";
        String text = dataPosition.getFriendName() + " visited " + dataPosition.getPoiName1() +" and " + dataPosition.getPoiName2();
        feedText.setText(text);

        TextView timestamp = (TextView) row.findViewById(R.id.timestamp);
        long diff = Calendar.getInstance().getTime().getTime() - dataPosition.getTimestamp().getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if( Math.abs(days) < 1){
            if(Math.abs(hours) < 1)
                timestamp.setText(minutes + "min");
            else
                timestamp.setText(hours + "h");
        }else
            timestamp.setText(days + " days");


        return row;
    }
}
