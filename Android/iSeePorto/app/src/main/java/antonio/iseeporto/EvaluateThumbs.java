package antonio.iseeporto;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
                if (((ColorDrawable) naoGostoB.getBackground()).getColor() == Color.WHITE) {
                    if (((ColorDrawable) gostoB.getBackground()).getColor() == Color.WHITE) {
                        gostoB.setBackgroundColor(Color.GREEN);
                        accessUrl("https://iseeporto.revtut.net/api/api.php?action=make_review&id="
                                + SingletonStringId.getInstance().getId()
                                + "&accesstoken=" + Singleton.getInstance().getAccessToken().getToken()
                                + "&comment=" + ""
                                + "&like=" + "1");
                        return;
                    }

                    gostoB.setBackgroundColor(Color.WHITE);
                    accessUrl("https://iseeporto.revtut.net/api/api.php?action=delete_review&id="
                            + SingletonStringId.getInstance().getId()
                            + "&accesstoken=" + Singleton.getInstance().getAccessToken().getToken());
                }
            }
        });

        naoGostoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ColorDrawable) gostoB.getBackground()).getColor() == Color.WHITE) {
                    if (((ColorDrawable) naoGostoB.getBackground()).getColor() == Color.WHITE) {
                        naoGostoB.setBackgroundColor(Color.RED);
                        accessUrl("https://iseeporto.revtut.net/api/api.php?action=make_review&id="
                                + SingletonStringId.getInstance().getId()
                                + "&accesstoken=" + Singleton.getInstance().getAccessToken().getToken()
                                + "&comment=" + ""
                                + "&like=" + "-1");
                    }
                    naoGostoB.setBackgroundColor(Color.WHITE);
                    accessUrl("https://iseeporto.revtut.net/api/api.php?action=delete_review&id="
                            + SingletonStringId.getInstance().getId()
                            + "&accesstoken=" + Singleton.getInstance().getAccessToken().getToken());
                }
            }
        });

        return view;
    }

    void accessUrl(String url)
    {
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
        temp.setActivity(getActivity());
        temp.execute(url);
    }
}
