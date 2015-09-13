package antonio.iseeporto;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Perfil extends Fragment {

    DownloaderImage downloadImage = new DownloaderImage();
    private ArrayList<AchievementsAdapter.AchievementData> achievementData = new ArrayList<>();
    private AchievementsAdapter aAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.perfil, container, false);

        facebookRequest();

        ListView listView = (ListView) view.findViewById(R.id.achievements_list_view);
        achievementData = new ArrayList<>();
        aAdapter = new AchievementsAdapter(inflater.getContext(), achievementData);
        listView.setAdapter(aAdapter);

        startInfoTransfer();

        return view;
    }

    private void facebookRequest()
    {
        final AccessToken tempToken = Singleton.getInstance().getAccessToken();

        //obtem as informacoes do facebook
//        GraphRequest request = GraphRequest.newMeRequest(
//                tempToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        // Application code
//                        //Log.e("response", "response" + object.toString());
//                        try {
//                            ((TextView) getView().findViewById(R.id.nomeId)).setText(object.getString("name"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,gender,birthday,picture.width(300)");
//        request.setParameters(parameters);
//        request.executeAsync();
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
                    objInfo = new JSONObject(data);
                    shortcut(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        temp.setActivity(getActivity());

        JSONAsyncTask temp2 = new JSONAsyncTask() {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                try {
                    System.out.println("POOOOOOOOST");
                    JSONArray arrayInfo = new JSONArray(data);
                    shortcut2(arrayInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        temp2.setActivity(getActivity());
        String url2 = "https://iseeporto.revtut.net/api/api.php?action=get_user_achievements&id=" + Singleton.getInstance().getAccessToken().getUserId();
        System.out.println(url2);
        temp2.execute(url2);

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
                ImageView tempImage = (ImageView) getView().findViewById(R.id.profile_pic_big);

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

    void shortcut2(JSONArray jsona) throws JSONException {
        achievementData.clear();
        System.out.println("Cenas " + jsona.length());
        for (int i = 0; i < jsona.length(); i++)
        {
            JSONObject sPoI = jsona.getJSONObject(i);
            AchievementsAdapter.AchievementData ad =
                    new AchievementsAdapter.AchievementData(
                            "https://iseeporto.revtut.net/uploads/achievements/" + sPoI.getInt("achievementId") + ".jpg",
                            sPoI.getInt("userId"),
                            sPoI.getString("name"),
                            sPoI.getString("description"));
            achievementData.add(ad);
        }
        aAdapter.notifyDataSetChanged();
    }
}
