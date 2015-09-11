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
 * Created by Duarte on 11/09/2015.
 */
public class AchievementsAdapter extends BaseAdapter {

    private Context context;
    private List<AchievementData> data;
    private View row ;

    protected static class AchievementData{
        private String imageURL;
        private int achievementID;
        private String achievementName;
        private String achievementDescription;

        AchievementData(String imageURL, int achievementID, String achievementName ,String achievementDescription){
            this.imageURL = imageURL;
            this.achievementID = achievementID;
            this.achievementName = achievementName;
            this.achievementDescription = achievementDescription;
        }


        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public int getAchievementID() {
            return achievementID;
        }

        public void setAchievementID(int achievementID) {
            this.achievementID = achievementID;
        }

        public String getAchievementName() {
            return achievementName;
        }

        public void setAchievementName(String achievementName) {
            this.achievementName = achievementName;
        }

        public String getAchievementDescription() {
            return achievementDescription;
        }

        public void setAchievementDescription(String achievementDescription) {
            this.achievementDescription = achievementDescription;
        }
    }

    AchievementsAdapter(Context context){
        this.context = context;
    }

    AchievementsAdapter(Context context, ArrayList<AchievementData> data){
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
        return data.get(position).achievementID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        row = inflater.inflate(R.layout.achievements_row, parent, false);

        AchievementData achievementData = this.data.get(position);

        ImageView image = (ImageView) row.findViewById(R.id.achievement_image);

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

        downloadImageTask.execute(achievementData.getImageURL());

        TextView name = (TextView) row.findViewById(R.id.achievement_name);
        name.setText(achievementData.getAchievementName());

        TextView description = (TextView) row.findViewById(R.id.achievement_description);
        description.setText(achievementData.achievementDescription);

        return row;
    }
}
