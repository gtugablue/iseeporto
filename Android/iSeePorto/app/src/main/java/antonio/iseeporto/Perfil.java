package antonio.iseeporto;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Perfil extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.perfil, container, false);

        final AccessToken tempToken = Singleton.getInstance().getAccessToken();

        //obtem as informacoes do facebook
        GraphRequest request = GraphRequest.newMeRequest(
                tempToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        //Log.e("response", "response" + object.toString());
                        try {
                            ((TextView) getView().findViewById(R.id.nomeId)).setText(object.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,picture.width(300)");
        request.setParameters(parameters);
        request.executeAsync();

        ListView listView = (ListView) view.findViewById(R.id.achievements_list_view);
        ArrayList<AchievementsAdapter.AchievementData> achievementData = new ArrayList<>();
        achievementData.add(new AchievementsAdapter.AchievementData("https://iseeporto.revtut.net/uploads/PoI_photos/18.jpg", 1, "Primeiro Achivement", "Fizeste a tua primeira review"));
        listView.setAdapter(new AchievementsAdapter(inflater.getContext(), achievementData));

        startInfoTransfer();

        return view;
    }

    protected void startInfoTransfer()
    {
        String url = "https://iseeporto.revtut.net/api/api.php?action=get_user_info&accessToken=" + Singleton.getInstance().getAccessToken().getToken();
        if (SingletonStringId.getInstance().getId() == null)
            new JSONAsyncTask().execute(url);
        else
            new JSONAsyncTask().execute(url + "&id=" + SingletonStringId.getInstance().getId());
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;
        JSONObject jsono;

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

                    jsono = new JSONObject(data);

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
                    ImageView tempImage = (ImageView) view.findViewById(R.id.profile_pic_big);

                    //define a imagem do utilizador
                    final DownloadImageTask downloadImageTask = new DownloadImageTask(tempImage){
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

                    try {
                        if (jsono != null)
                        {
                            ((TextView) view.findViewById(R.id.visitedPlacesId)).setText("Number of Visited Places: " + jsono.getString("numVisits"));
                            ((TextView) view.findViewById(R.id.pointsId)).setText("Points: " + jsono.getString("points"));
                            downloadImageTask.execute("https://graph.facebook.com/" + jsono.getString("idFacebook") + "/picture?width=500&height=500");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
