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
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private List<VisitedPoiData> data;

    VisitedPlacesAdapter(Context context){
        this.context = context;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        ArrayList<VisitedPoiData> data = new ArrayList<>();
        List<String> names = Arrays.asList(context.getResources().getStringArray(R.array.poi_names));
        List<String> dates = Arrays.asList(context.getResources().getStringArray(R.array.visited_dates));
        int[] feedback = context.getResources().getIntArray(R.array.visited_feedback);

        for(int i = 0; i < names.size(); i++)
            try {
                Date date = format.parse(dates.get(i));
                data.add(new VisitedPoiData(i,"https://iseeporto.revtut.net/uploads/PoI_photos/19.jpg", names.get(i), date, feedback[i] == 1 ? true : false));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        this.data = data;

    }

    VisitedPlacesAdapter(Context context, List<VisitedPoiData> data){
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
        row = inflater.inflate(R.layout.visited_pois_row_layout, parent, false);

        VisitedPoiData rowData = data.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.poi_image);
        DownloadImageTask downloadImageTask = new DownloadImageTask(image){

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
