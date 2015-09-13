package antonio.iseeporto.uielements;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import antonio.iseeporto.JSONAsyncTask;
import antonio.iseeporto.R;
import antonio.iseeporto.Singleton;
import antonio.iseeporto.SingletonStringId;

/**
 * Created by Antonio on 08-09-2015.
 */
public class EvaluateThumbs extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evaluate_thumbs, container, false);

        ImageButton desmarcarB = (ImageButton) view.findViewById(R.id.desmarcarB);
        desmarcarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Place) getFragmentManager().findFragmentByTag("Place")).retirarVisita();
            }
        });

        final ImageButton gostoB = (ImageButton) view.findViewById(R.id.gostoB);
        final ImageButton naoGostoB = (ImageButton) view.findViewById(R.id.naoGostoB);
        gostoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonStringId.getInstance().setLike(true);
                if (((ColorDrawable) naoGostoB.getBackground()).getColor() == 0x00FFFFFF) {
                    if (((ColorDrawable) gostoB.getBackground()).getColor() == 0x00FFFFFF) {
                        gostoB.setBackgroundColor(0xFF00FF00);
                        /*accessUrl("https://iseeporto.revtut.net/api/api.php?action=make_review&id="
                                + SingletonStringId.getInstance().getId()
                                + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken()
                                + "&comment=" + ""
                                + "&like=" + "1");*/
                        SingletonStringId.getInstance().setLike(true);
                        Intent searchIntent = new Intent(v.getContext(), WriteReview.class);
                        startActivity(searchIntent);
                        return;
                    }

                    gostoB.setBackgroundColor(0x00FFFFFF);
                    accessUrl("https://iseeporto.revtut.net/api/api.php?action=delete_review&id="
                            + SingletonStringId.getInstance().getId()
                            + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
                    SingletonStringId.getInstance().setLike(false);
                }

            }
        });

        naoGostoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((ColorDrawable) gostoB.getBackground()).getColor() == 0x00FFFFFF) {
                    if (((ColorDrawable) naoGostoB.getBackground()).getColor() == 0x00FFFFFF) {
                        naoGostoB.setBackgroundColor(0xFFFF0000);
                        /*accessUrl("https://iseeporto.revtut.net/api/api.php?action=make_review&id="
                                + SingletonStringId.getInstance().getId()
                                + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken()
                                + "&comment=" + ""
                                + "&like=" + "0");*/
                        SingletonStringId.getInstance().setLike(false);
                        Intent searchIntent = new Intent(v.getContext(), WriteReview.class);
                        startActivity(searchIntent);
                        return;
                    }
                    naoGostoB.setBackgroundColor(0x00FFFFFF);
                    accessUrl("https://iseeporto.revtut.net/api/api.php?action=delete_review&id="
                            + SingletonStringId.getInstance().getId()
                            + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken());
                    SingletonStringId.getInstance().setLike(true);
                }
            }
        });

        return view;
    }

    void accessUrl(String url)
    {
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
        temp.setActivity(getActivity());
        temp.execute(url);
    }
}
