package antonio.iseeporto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestedMenu.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestedMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestedMenu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<SuggestedPlacesAdapter.SuggestedPoiData> data;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestedMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestedMenu newInstance(String param1, String param2) {
        SuggestedMenu fragment = new SuggestedMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SuggestedMenu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_menu, container, false);
        ListView listView = (ListView) view.findViewById(R.id.suggested_list_view);
        SuggestedPlacesAdapter spAdapter = new SuggestedPlacesAdapter(inflater.getContext());
        data = spAdapter.getData();
        listView.setAdapter(new SuggestedPlacesAdapter(inflater.getContext()));

        FloatingActionButton searchButton = (FloatingActionButton) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(inflater.getContext(), Search.class);
                startActivity(searchIntent);
            }
        });

        task.setActivity(this.getActivity());
        startInfoTransfer();

        return view;
    }

    public void startInfoTransfer()
    {
        String url = "https://iseeporto.revtut.net/api/api.php?action=get_suggested_pois&currLat=1&currLon=1&minDist=0&maxDist=5000&accessToken=" + Singleton.getInstance().getAccessToken().getToken();
        System.out.println("URL: " + url);
        task.execute(url);
    }

    private JSONAsyncTask task = new JSONAsyncTask() {
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                return;
            }
            try {
                JSONArray jsona = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
