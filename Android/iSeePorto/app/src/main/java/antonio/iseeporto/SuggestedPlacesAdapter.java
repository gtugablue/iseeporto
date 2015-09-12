package antonio.iseeporto;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duarte on 08/09/2015.
 */
public class SuggestedPlacesAdapter extends BaseAdapter {

    private final Context context;
    View row ;

    public List<SuggestedPoiData> getData() {
        return data;
    }

    private List<SuggestedPoiData> data;

    public static class SuggestedPoiData{
        protected int poiID;
        protected String poiImageURL;
        protected String poiName;
        protected String address;
        protected int distance;
        protected Bitmap[] bitmapArray = new Bitmap[1];

        SuggestedPoiData(View view, int poiID,String poiImageURL,String poiName, String address,int distance){
            this.poiID = poiID;
            this.poiImageURL = poiImageURL;
            this.poiName = poiName;
            this.address = address;
            this.distance = distance;
            DownloadImageTask downloadImageTask = new DownloadImageTask(bitmapArray);
            downloadImageTask.execute(poiImageURL);
        }

        public int getPoiID() {
            return poiID;
        }

        public String getPoiName() {
            return poiName;
        }

        public String getAddress() {
            return address;
        }

        public int getDistance() {
            return distance;
        }

        public String getPoiImageURL() {
            return poiImageURL;
        }
    }



    public SuggestedPlacesAdapter(Context context){
        this.context = context;
        ArrayList<SuggestedPoiData> data;
        data = new ArrayList<>();
       /* List<String> names =Arrays.asList(context.getResources().getStringArray(R.array.poi_names));
        List<String> visitors = Arrays.asList(context.getResources().getStringArray(R.array.friends_list));
        int[] distances = context.getResources().getIntArray(R.array.distances);

        for(int i = 0; i< distances.length; i++){
            data.add(new SuggestedPoiData(i,"https://iseeporto.revtut.net/uploads/PoI_photos/18.jpg", names.get(i), visitors.get(i), distances[i]));
        }
        */
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
        row = inflater.inflate(R.layout.suggested_pois_row_layout, parent, false);
        SuggestedPoiData poiData = data.get(position);

        if (poiData.bitmapArray[0] == null) {
            ImageView image = (ImageView) row.findViewById(R.id.poi_image);
            DownloadImageTask downloadImageTask = new DownloadImageTask(image) {

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
            downloadImageTask.execute(poiData.poiImageURL);
        }

        TextView name = (TextView) row.findViewById(R.id.poi_name);
        name.setText(poiData.getPoiName().toString());

        TextView visitors = (TextView) row.findViewById(R.id.visitors);
        visitors.setText(data.get(position).getAddress());

        TextView distance = (TextView) row.findViewById(R.id.distance);
        if(poiData.getDistance() > 1000)
            distance.setText(((float)( poiData.getDistance() / 100 )) / 10 + "km");
        else
            distance.setText(poiData.getDistance() + "m");


        return row;
    }
}
