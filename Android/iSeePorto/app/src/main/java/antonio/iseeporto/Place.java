package antonio.iseeporto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Place extends android.app.Fragment {

    Double latitude, longitude;
    Boolean report = false;
    ImageView tempImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewTemp = inflater.inflate(R.layout.place, container, false);
        createVisitFrag();
        tempImage = (ImageView) viewTemp.findViewById(R.id.placePicId);
        new JSONAsyncTask().execute("https://iseeporto.revtut.net/api/api.php?action=get_poi_info&id="+SingletonStringId.getInstance().getId());
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




    ////////////////////////////////////////////////////////////
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

        //define a imagem do utilizador
        final DownloadImageTask downloadImageTask = new DownloadImageTask(tempImage){
            @Override
            protected void onPostExecute(final Bitmap result) {
                getView().post(new Runnable() {
                    @Override
                    public void run() {
                        bmImage.setImageBitmap(result);
                    }
                });
            }
        };

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            //notifyAll();
            /*adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();*/
            getView().post(new Runnable() {
                @Override
                public void run() {

                    Log.e("Report ----> ", Boolean.toString(report));
                    if (!report) {
                        try {
                            Log.e("Passou", "----> Condicao");
                            if (jsono != null) {
                                Log.e("Passed--->", "Here");
                                ((TextView) getView().findViewById(R.id.namePlaceId)).setText(jsono.getString("name"));
                                ((TextView) getView().findViewById(R.id.idAddress)).setText(jsono.getString("address"));
                                ((TextView) getView().findViewById(R.id.idDescription)).setText(jsono.getString("description"));
                                latitude = Double.parseDouble(jsono.getString("latitude"));
                                longitude = Double.parseDouble(jsono.getString("longitude"));
                                ((TextView) getView().findViewById(R.id.placeInfoId)).setText(
                                        jsono.getString("numVisits") + " visits, " +
                                                jsono.getString("numLikes") + " likes and " +
                                                jsono.getString("numDislikes") + " dislikes");
                                downloadImageTask.execute("https://iseeporto.revtut.net/uploads/PoI_photos/" + jsono.getString("id") + ".jpg");
                                setTextsListeners(jsono.getString("id"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void openNavigation(double latitude, double longitude) {
        //o ponto de partida é o local onde o utilizador está
        Intent intent1 = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude));
        startActivity(intent1);
    }

    private void setTextsListeners(final String idPlace)
    {
        ((TextView) getView().findViewById(R.id.idAddress)).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                //abre o mapa
                openNavigation(latitude, longitude);

                //se chega aqui é porque alguma coisa falhou
                return false;
            }
        });

        ((TextView) getView().findViewById(R.id.reportId)).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                new JSONAsyncTask().execute("https://iseeporto.revtut.net/api/api.php?action=report&id="+
                        idPlace+ "&accesstoken=" + Singleton.getInstance().getAccessToken().getToken());
                Toast.makeText(getActivity().getApplicationContext(), "Report Sent", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
