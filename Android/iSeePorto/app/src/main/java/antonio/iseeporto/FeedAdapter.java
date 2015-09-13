package antonio.iseeporto;

import android.content.Context;
import android.graphics.Bitmap;
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
    View row ;

    public class FeedData{

        protected String poiImage1;
        protected String poiImage2;
        protected String poiName1;
        protected String poiName2;
        protected int friendID;
        protected String friendImageURL;
        protected String friendName;
        protected Date timestamp;

        FeedData(String poiImage1, String poiImage2, String poiName1, String poiName2,int friendID, String friendImageURL, String friendName, Date timestamp){
            this.poiImage1 = poiImage1;
            this.poiImage2 = poiImage2;
            this.poiName1 = poiName1;
            this.poiName2 = poiName2;
            this.friendID = friendID;
            this.friendImageURL = friendImageURL;
            this.friendName = friendName;
            this.timestamp = timestamp;
        }

        FeedData(String poiName1,String poiName2,int friendID, String friendImageURL, String friendName, Date timestamp){
            this.poiName1 = poiName1;
            this.poiName2 = poiName2;
            this.friendID = friendID;
            this.friendImageURL = friendImageURL;
            this.friendName = friendName;
            this.timestamp = timestamp;
        }

        FeedData(String poiName1,int friendID, String friendImageURL, String friendName, Date timestamp){
            this.poiName1 = poiName1;
            this.friendID = friendID;
            this.friendImageURL = friendImageURL;
            this.friendName = friendName;
            this.timestamp = timestamp;
        }


        public String getPoiImage1() {
            return poiImage1;
        }

        public String getPoiImage2() {
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

        public String getFriendImageURL() {
            return friendImageURL;
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
                data.add(new FeedData("Clérigos", "Ribeira",i, "https://iseeporto.revtut.net/uploads/PoI_photos/18.jpg", friends.get(i),date));
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
        row = inflater.inflate(R.layout.feed_row_layout, parent, false);
        FeedData dataPosition = data.get(position);

//        ImageView poiImage1 = (ImageView) row.findViewById(R.id.poi_image1);
//        DownloadImageTask downloadImageTask1 = new DownloadImageTask(poiImage1){
//
//            @Override
//            protected void onPostExecute(final Bitmap result) {
//                row.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        bmImage.setImageBitmap(result);
//                    }
//                });
//            }
//        };
//
//
//        ImageView poiImage2 = (ImageView) row.findViewById(R.id.poi_image2);
//        DownloadImageTask downloadImageTask2 = new DownloadImageTask(poiImage2){
//
//            @Override
//            protected void onPostExecute(final Bitmap result) {
//                row.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        bmImage.setImageBitmap(result);
//                    }
//                });
//            }
//        };
//
//        downloadImageTask1.execute(dataPosition.getPoiImage1());
//
//        downloadImageTask2.execute(dataPosition.getPoiImage2());

        ImageView friendImage = (ImageView) row.findViewById(R.id.friend_user_image);
        DownloadImageTask downloadImageTask = new DownloadImageTask(friendImage){

            @Override
            protected void onPostExecute(final Bitmap result) {
                row.post(new Runnable() {
                    @Override
                    public void run() {
                        bmImage.setImageBitmap(result);
                    }
                });
            }
        };

        downloadImageTask.execute(dataPosition.getFriendImageURL());


        TextView feedText = (TextView) row.findViewById(R.id.feedText);
        String text = dataPosition.getFriendName() + " visited " + dataPosition.getPoiName1() + (dataPosition.getPoiName2() == null ? "" : (" and " + dataPosition.getPoiName2()));
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
