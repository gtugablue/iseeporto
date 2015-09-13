package antonio.iseeporto.uielements;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import antonio.iseeporto.DownloaderImage;
import antonio.iseeporto.JSONAsyncTask;
import antonio.iseeporto.R;
import antonio.iseeporto.Singleton;
import antonio.iseeporto.SingletonStringId;
import antonio.iseeporto.uielements.listviewadapters.ReviewAdapter;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Place extends android.app.Fragment {

    Double latitude, longitude;
    Boolean report = false; Boolean checkVisited = false; Boolean checkLiked = false;
    DownloaderImage downloadImage = new DownloaderImage();
    TextView addressText, reportText;
    JSONObject objInfo;
    Boolean infoTransferedBoolean = false;
    String infoTransferedText = "";
    LayoutInflater inflater;
    ReviewAdapter rAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View viewTemp = inflater.inflate(R.layout.place, container, false);
        TextView tv1 = (TextView)viewTemp.findViewById(R.id.namePlaceId);
        tv1.setSelected(true);
        TextView tv2 = (TextView)viewTemp.findViewById(R.id.idAddress);
        tv2.setSelected(true);
        report = false;
        executeUrl("https://iseeporto.revtut.net/api/api.php?action=get_poi_info&id=" + SingletonStringId.getInstance().getId());

        ListView reviewsList = (ListView) viewTemp.findViewById(R.id.reviews_list);
        rAdapter = new ReviewAdapter(getActivity().getApplicationContext());
        reviewsList.setAdapter(rAdapter);
        downloadData();

        return viewTemp;
    }

    private void downloadData() {
        String url = "https://iseeporto.revtut.net/api/api.php?action=get_reviews&id=" + SingletonStringId.getInstance().getId();

        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                try {
                    JSONArray array = new JSONArray(data);
                    shortcutReviews(array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        temp.setActivity(getActivity());
        temp.execute(url);
    }

    private void shortcutReviews(JSONArray array) throws JSONException {
        List<ReviewAdapter.ReviewData> data = rAdapter.getData();
        data.clear();
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject review = array.getJSONObject(i);
            long userId = review.getLong("userId");
            Boolean like = review.getInt("like") != 0;
            ReviewAdapter.ReviewData rd =
                    new ReviewAdapter.ReviewData(
                            userId,
                            "https://graph.facebook.com/" + userId + "/picture?width=100&height=100",
                            review.getString("name"),
                            review.getString("comment"),
                            like);
            data.add(rd);
        }
        rAdapter.notifyDataSetChanged();
    }

    void executeUrl(String url)
    {
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (!result) {
                    return;
                }
                try {
                    if (checkVisited)
                    {
                        infoTransferedBoolean = Boolean.valueOf(data);
                    }
                    else if (checkLiked)
                    {
                        infoTransferedText = data;
                    }
                    else {
                        objInfo = new JSONObject(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shortcut();
            }
        };
        temp.setActivity(getActivity());

        temp.execute(url);
    }

    public void createVisitFrag()
    {
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        VisitButton v = new VisitButton();
        transaction.add(R.id.avaliacao, v, "VisitButton");
        transaction.commit();
    }

    public void visitarLocal() {
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
        temp.setActivity(getActivity());
        temp.execute("https://iseeporto.revtut.net/api/api.php?action=set_visited&id="
                + SingletonStringId.getInstance().getId()
                + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
        visitarLocalAfter();
    }

    public void visitarLocalAfter() {
        EvaluateThumbs avaliacao = new EvaluateThumbs();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.avaliacao, avaliacao, "Avaliar");
        transaction.commit();
    }

    public void retirarVisita()
    {
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
        temp.setActivity(getActivity());
        temp.execute("https://iseeporto.revtut.net/api/api.php?action=delete_review&id="
                + SingletonStringId.getInstance().getId()
                + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());

        temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
        temp.setActivity(getActivity());
        temp.execute("https://iseeporto.revtut.net/api/api.php?action=set_not_visited&id="
                + SingletonStringId.getInstance().getId()
                + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());

        VisitButton v = new VisitButton();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.avaliacao, v, "VisitButton");
        transaction.commit();
    }

    private void shortcut()
    {
        getView().post(new Runnable() {
            @Override
            public void run() {

                if (!report && !checkVisited && !checkLiked) {
                    try {
                        if (objInfo != null) {
                            ((TextView) getView().findViewById(R.id.namePlaceId)).setText(objInfo.getString("name"));
                            addressText = (TextView) getView().findViewById(R.id.idAddress);
                            addressText.setText(objInfo.getString("address"));
                            reportText = (TextView) getView().findViewById(R.id.reportId);
                            ((TextView) getView().findViewById(R.id.idDescription)).setText(objInfo.getString("description"));
                            latitude = Double.parseDouble(objInfo.getString("latitude"));
                            longitude = Double.parseDouble(objInfo.getString("longitude"));
                            ((TextView) getView().findViewById(R.id.placeInfoId)).setText(
                                    objInfo.getString("numVisits") + " visits, " +
                                            objInfo.getString("numLikes") + " likes and " +
                                            objInfo.getString("numDislikes") + " dislikes");
                            downloadImage.downloadImage((ImageView) getView().findViewById(R.id.placePicId), getView(), "https://iseeporto.revtut.net/uploads/PoI_photos/" + SingletonStringId.getInstance().getId() + ".jpg");
                            setClicks();
                            createVisitFrag();

                            checkVisited = true;
                            executeUrl("https://iseeporto.revtut.net/api/api.php?action=has_visited&id=" + SingletonStringId.getInstance().getId()
                                    + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
                            return;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else report = false;

                if (checkVisited)
                {
                    checkVisited = false;
                    if (infoTransferedBoolean) {
                        visitarLocalAfter();
                        checkLiked = true;
                        executeUrl("https://iseeporto.revtut.net/api/api.php?action=has_liked&id=" + SingletonStringId.getInstance().getId()
                                + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
                        return;
                    }
                }

                if (checkLiked)
                {
                    checkLiked = false;
                    if (infoTransferedText.equals("1"))
                    {
                        getView().findViewById(R.id.gostoB).setBackgroundColor(Color.GREEN);
                    }
                    else if (infoTransferedText.equals("0"))
                    {
                        getView().findViewById(R.id.naoGostoB).setBackgroundColor(Color.RED);
                    }

                }

            }
        });
    }

    private void openNavigation(double latitude, double longitude) {
        //o ponto de partida é o local onde o utilizador está
        Intent intent1 = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude));
        startActivity(intent1);
    }

    private void setClicks()
    {
        reportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report = true;
                JSONAsyncTask tempTask = new JSONAsyncTask();
                tempTask.setActivity(getActivity());
                tempTask.execute("https://iseeporto.revtut.net/api/api.php?action=report&id=" +
                        SingletonStringId.getInstance().getId() + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
                Toast.makeText(getActivity().getApplicationContext(), "Report Sent", Toast.LENGTH_SHORT).show();
            }
        });

        addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abre o mapa
                openNavigation(latitude, longitude);
            }
        });
    }
}
