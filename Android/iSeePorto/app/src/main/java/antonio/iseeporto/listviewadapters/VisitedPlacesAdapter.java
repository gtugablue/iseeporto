package antonio.iseeporto.listviewadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import antonio.iseeporto.DownloadImageTask;
import antonio.iseeporto.JSONAsyncTask;
import antonio.iseeporto.R;
import antonio.iseeporto.Singleton;

/**
 * Created by Duarte on 09/09/2015.
 */
public class VisitedPlacesAdapter extends BaseAdapter {

    private final Context context;
    View row;

    public class VisitedPoiData{


        protected int poiID;
        protected String poiImageURL;
        protected String poiName;
        protected Date date;
        protected boolean liked;

        VisitedPoiData(int poiID, String poiImageURL , String poiName, Date date, boolean liked){
            this.poiImageURL = poiImageURL;
            this.poiID = poiID;
            this.poiName = poiName;
            this.date = date;
            this.liked = liked;
        }

        public int getPoiID() {
            return poiID;
        }

        public String getPoiName() {
            return poiName;
        }

        public Date getDate() {
            return date;
        }

        public boolean isLiked() {
            return liked;
        }

        public String getPoiImageURL() {
            return poiImageURL;
        }

        public void setPoiImageURL(String poiImageURL) {
            this.poiImageURL = poiImageURL;
        }


    }

    private List<VisitedPoiData> dataV;

    public VisitedPlacesAdapter(Context context, Activity act){
        this.context = context;
        final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dataV = new ArrayList<>();

        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                try {
                    JSONArray temp = new JSONArray(data);
                    Log.e("DATA = ", data);
                    Date date; int id; String pic, name, like;

                    for(int i = 0; i < temp.length(); i++)
                        try {
                            JSONObject obj = temp.getJSONObject(i);

                            id = Integer.parseInt(obj.getString("poiId"));
                            pic = "https://iseeporto.revtut.net/uploads/PoI_photos/"+id+".jpg";
                            name = obj.getString("name");
                            date = format.parse(obj.getString("visitDate"));
                            //like = obj.getString("like");

                            dataV.add(new VisitedPoiData(i,pic, name, date, 1 == 1));
                            VisitedPlacesAdapter.this.notifyDataSetChanged();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        temp.setActivity(act);
        temp.execute("https://iseeporto.revtut.net/api/api.php?action=get_visited&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
    }

    VisitedPlacesAdapter(Context context, List<VisitedPoiData> dataV){
        this.context = context;
        this.dataV = dataV;
    }

    @Override
    public int getCount() {
        return dataV.size();
    }

    @Override
    public Object getItem(int position) {
        return dataV.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataV.get(position).getPoiID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        row = inflater.inflate(R.layout.visited_pois_row_layout, parent, false);

        VisitedPoiData rowData = dataV.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.poi_image);
        DownloadImageTask downloadImageTask = new DownloadImageTask(image){

            @Override
            protected void onPostExecute(final Bitmap result) {
                row.post(new Runnable() {
                    @Override
                    public void run() {
                        getBmImage().setImageBitmap(result);
                    }
                });
            }
        };

        downloadImageTask.execute(rowData.getPoiImageURL());

        TextView name = (TextView) row.findViewById(R.id.poi_name);
        name.setText(rowData.getPoiName().toString());

        TextView date = (TextView) row.findViewById(R.id.visitation_date);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String dateString = format.format(rowData.getDate());
        date.setText(dateString);

        ImageView like = (ImageView) row.findViewById(R.id.thumb);
        if(rowData.isLiked())
            like.setImageResource(R.mipmap.thumbs_up);
        else
            like.setImageResource(R.mipmap.thumbs_down);

        return row;
    }
}
