package antonio.iseeporto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Place extends android.app.Fragment {

    Double latitude, longitude;
    Boolean report = false;
    String id;
    DownloaderImage downloadImage = new DownloaderImage();
    TextView addressText, reportText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewTemp = inflater.inflate(R.layout.place, container, false);
        getInfoTask.setActivity(getActivity());
        createVisitFrag();
        report = false;
        getInfoTask.execute("https://iseeporto.revtut.net/api/api.php?action=get_poi_info&id=" + SingletonStringId.getInstance().getId());
        return viewTemp;
    }

    public void createVisitFrag()
    {
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        Visitar v = new Visitar();
        transaction.add(R.id.avaliacao, v, "Visitar");
        transaction.commit();
    }

    public void visitarLocal()
    {
        EvaluateThumbs avaliacao = new EvaluateThumbs();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.avaliacao, avaliacao, "Avaliar");
        transaction.commit();
    }

    public void retirarVisita()
    {
        Visitar v = new Visitar();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.avaliacao, v, "Visitar");
        transaction.commit();
    }

    private JSONAsyncTask getInfoTask = new JSONAsyncTask() {
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                return;
            }

            getView().post(new Runnable() {
                @Override
                public void run() {

                    if (!report) {
                        try {
                            if (jsono != null) {
                                ((TextView) getView().findViewById(R.id.namePlaceId)).setText(jsono.getString("name"));
                                addressText = (TextView) getView().findViewById(R.id.idAddress);
                                addressText.setText(jsono.getString("address"));
                                reportText = (TextView) getView().findViewById(R.id.reportId);
                                ((TextView) getView().findViewById(R.id.idDescription)).setText(jsono.getString("description"));
                                latitude = Double.parseDouble(jsono.getString("latitude"));
                                longitude = Double.parseDouble(jsono.getString("longitude"));
                                ((TextView) getView().findViewById(R.id.placeInfoId)).setText(
                                        jsono.getString("numVisits") + " visits, " +
                                                jsono.getString("numLikes") + " likes and " +
                                                jsono.getString("numDislikes") + " dislikes");
                                id = jsono.getString("id");
                                downloadImage.downloadImage((ImageView) getView().findViewById(R.id.placePicId), getView(), "https://iseeporto.revtut.net/uploads/PoI_photos/" + id + ".jpg");
                                setClicks();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    };

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
                        id + "&accesstoken=" + Singleton.getInstance().getAccessToken().getToken());
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
