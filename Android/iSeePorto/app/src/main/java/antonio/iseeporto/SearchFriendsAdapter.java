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
 * Created by Duarte on 12/09/2015.
 */
public class SearchFriendsAdapter extends BaseAdapter {

    private Context context;
    private List<SearchResults> data;
    private View row;

    public class SearchResults{

        private final String userImageURL;
        private final String userName;
        private final int userID;

        SearchResults(int userID, String userImageURL, String userName){
            this.userImageURL = userImageURL;
            this.userName = userName;
            this.userID = userID;
        }


        public String getUserImageURL() {
            return userImageURL;
        }

        public String getUserName() {
            return userName;
        }

        public int getUserID() {
            return userID;
        }
    }

    SearchFriendsAdapter(Context context, ArrayList<SearchResults> data){
        this.context = context;
        this.data = data;
    }

    SearchFriendsAdapter(Context context){
        this.context = context;
        this.data = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            this.data.add(new SearchResults(i, "https://iseeporto.revtut.net/uploads/PoI_photos/18.jpg", "Rogério Costa"));
        }
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
        return data.get(position).getUserID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        row = inflater.inflate(R.layout.search_result_row, parent, false);
        SearchResults searchResults = data.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.friend_user_image);
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

        downloadImageTask.execute(searchResults.getUserImageURL());


        TextView name = (TextView) row.findViewById(R.id.user_name);
        name.setText(searchResults.getUserName());

        return row;
    }
}
