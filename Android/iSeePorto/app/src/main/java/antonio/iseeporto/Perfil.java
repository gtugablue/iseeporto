package antonio.iseeporto;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Perfil extends Fragment {

    String rec;
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.perfil, container, false);
        AsyncTask task = new JSONAsyncTask().execute("http://iseeporto.revtut.net/api/api.php?action=get_poi_info&id=1");


        ImageView tempImage = (ImageView) view.findViewById(R.id.profile_pic_big);

        DownloadImageTask downloadImageTask = new DownloadImageTask(tempImage){

            @Override
            protected void onPostExecute(final Bitmap result) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        bmImage.setImageBitmap(result);
                    }
                });
            }
        };

        downloadImageTask.execute("http://www.sunbeltsys.com/images/http_icon.jpg");

        ListView listView = (ListView) view.findViewById(R.id.achievements_list_view);
        ArrayList<AchievementsAdapter.AchievementData> achievementData = new ArrayList<>();
        achievementData.add(new AchievementsAdapter.AchievementData("http://www.sunbeltsys.com/images/http_icon.jpg", 1, "Primeiro Achivement", "Fizeste a tua primeira review"));
        listView.setAdapter(new AchievementsAdapter(inflater.getContext(), achievementData));



        return view;
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);

                    rec = jsono.getString("name");

                    return true;
                }

                //------------------>>

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            //notifyAll();
            /*adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();*/
            getView().post(new Runnable() {
                @Override
                public void run() {
                    ((TextView)(getView().findViewById(R.id.nomeId))).setText(rec);
                }
            });
        }
    }
}
