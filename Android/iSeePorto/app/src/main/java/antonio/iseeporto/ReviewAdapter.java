package antonio.iseeporto;

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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duarte on 13/09/2015.
 */
public class ReviewAdapter extends BaseAdapter {

    private final Context context;
    View row;
    private List<ReviewData> dataV;

    public class ReviewData{


        protected int userID;
        protected String userImageURL;
        protected String userName;
        protected String comment;
        protected boolean liked;

        ReviewData(int userID, String userImageURL, String userName, String comment, boolean liked){
            this.userImageURL = userImageURL;
            this.userID = userID;
            this.userName = userName;
            this.comment = comment;
            this.liked = liked;
        }

        public int getUserID() {
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


    ReviewAdapter(Context context, Activity activity){
        this.context = context;
        dataV = new ArrayList<>();

        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                try {
                    JSONArray temp = new JSONArray(data);
                    Log.e("DATA = ", data);
                    String date; int id; String pic, name, like;

                    for(int i = 0; i < temp.length(); i++)
                        try {
                            JSONObject obj = temp.getJSONObject(i);

                            id = Integer.parseInt(obj.getString("poiId"));
                            pic = "https://iseeporto.revtut.net/uploads/PoI_photos/"+id+".jpg";
                            name = obj.getString("name");
                            date = format.parse(obj.getString("visitDate"));
                            like = obj.getString("like");

                            dataV.add(new ReviewData(i,pic, name, date, 1 == 1));
                            ReviewAdapter.this.notifyDataSetChanged();
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

    ReviewAdapter(Context context, List<ReviewData> dataV){
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
        return dataV.get(position).getUserID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        row = inflater.inflate(R.layout.review_row, parent, false);

        ReviewData rowData = dataV.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.user_image);
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

        downloadImageTask.execute(rowData.getUserImageURL());

        TextView name = (TextView) row.findViewById(R.id.user_name);
        name.setText(rowData.getUserName().toString());

        TextView comment = (TextView) row.findViewById(R.id.comment);
        comment.setText(rowData.getComment());

        ImageView like = (ImageView) row.findViewById(R.id.thumb);
        if(rowData.isLiked())
            like.setImageResource(R.mipmap.thumbs_up);
        else
            like.setImageResource(R.mipmap.thumbs_down);

        return row;
    }


    JSONObject objInfo;

    protected void startInfoTransfer()
    {
        String url = "https://iseeporto.revtut.net/api/api.php?action=get_user_info&accessToken=" + Singleton.getInstance().getAccessToken().getToken();

        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                try {
                    objInfo = new JSONObject(data+"");
                    shortcut(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        temp.setActivity(getActivity());


        if (SingletonStringId.getInstance().getId() == null)
            temp.execute(url);
        else
            temp.execute(url + "&id=" + SingletonStringId.getInstance().getId());
    }

    void shortcut(Boolean result)
    {
        if (!result) {
            return;
        }

        getView().post(new Runnable() {
            @Override
            public void run() {
                ImageView tempImage = (ImageView) .findViewById(R.id.profile_pic_big);

                try {
                    if (objInfo != null) {
                        ((TextView) getView().findViewById(R.id.visitedPlacesId)).setText("Number of Visited Places: " + objInfo.getString("numVisits"));
                        ((TextView) getView().findViewById(R.id.pointsId)).setText("Points: " + objInfo.getString("points"));
                        ((TextView) getView().findViewById(R.id.nomeId)).setText(objInfo.getString("name"));

                        downloadImage.downloadImage(tempImage, getView(), "https://graph.facebook.com/" + objInfo.getString("idFacebook") + "/picture?width=500&height=500");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
