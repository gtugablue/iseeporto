package antonio.iseeporto.uielements.listviewadapters;

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

import antonio.iseeporto.DownloadImageTask;
import antonio.iseeporto.R;

/**
 * Created by Duarte on 13/09/2015.
 */
public class ReviewAdapter extends BaseAdapter {

    private final Context context;
    View row;

    public List<ReviewData> getData() {
        return data;
    }

    private List<ReviewData> data;

    public static class ReviewData {
        protected long userID;
        protected String userImageURL;
        protected String userName;
        protected String comment;
        protected boolean liked;

        public ReviewData(long userID, String userImageURL, String userName, String comment, boolean liked) {
            this.userImageURL = userImageURL;
            this.userID = userID;
            this.userName = userName;
            this.comment = comment;
            this.liked = liked;
        }

        public long getUserID() {
            return userID;
        }

        public String getUserName() {
            return userName;
        }

        public String getComment() {
            return comment;
        }

        public boolean isLiked() {
            return liked;
        }

        public String getUserImageURL() {
            return userImageURL;
        }

        public void setUserImageURL(String userImageURL) {
            this.userImageURL = userImageURL;
        }


    }


    public ReviewAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public ReviewAdapter(Context context, List<ReviewData> dataV) {
        this.context = context;
        this.data = dataV;
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
        row = inflater.inflate(R.layout.review_row, parent, false);

        ReviewData rowData = data.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.user_image);
        DownloadImageTask downloadImageTask = new DownloadImageTask(image) {

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

        downloadImageTask.execute(rowData.getUserImageURL());

        TextView name = (TextView) row.findViewById(R.id.user_name);
        name.setText(rowData.getUserName());

        TextView comment = (TextView) row.findViewById(R.id.comment);
        comment.setText(rowData.getComment());

        ImageView like = (ImageView) row.findViewById(R.id.thumb);
        if (rowData.isLiked())
            like.setImageResource(R.mipmap.thumbs_up);
        else
            like.setImageResource(R.mipmap.thumbs_down);

        return row;
    }
}
