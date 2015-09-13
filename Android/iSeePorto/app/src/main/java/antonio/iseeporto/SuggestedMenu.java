package antonio.iseeporto;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import antonio.iseeporto.listviewadapters.SuggestedPlacesAdapter;


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
    private SuggestedPlacesAdapter spAdapter;
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
        spAdapter = new SuggestedPlacesAdapter(inflater.getContext());
        data = spAdapter.getData();
        listView.setAdapter(spAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuggestedMenu.this.getActivity(), SearchPoiResult.class);
                SingletonStringId.getInstance().setId(id + "");
                startActivity(intent);
            }
        });

        FloatingActionButton searchButton = (FloatingActionButton) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(inflater.getContext(), SearchPoi.class);
                startActivity(searchIntent);
            }
        });

        startInfoTransfer();

        return view;
    }

    public void startInfoTransfer()
    {
        double latitude = Singleton.getInstance().getLatitude();
        double longitude = Singleton.getInstance().getLongitude();
        // No time to implement this properly and get the location.
        String url = "https://iseeporto.revtut.net/api/api.php?action=get_suggested_pois&currLat=" + latitude + "&currLon=" + longitude + "&minDist=0&maxDist=5000&accessToken=" + Singleton.getInstance().getAccessToken().getToken();

        System.out.println("URL: " + url);
        executeUrl(url);
    }

    void executeUrl(String url)
    {
        JSONAsyncTask task = new JSONAsyncTask() {
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (!result) {
                    System.err.println("Error reading data from the server.");
                    return;
                }
                try {
                    if (data == null)
                    {
                        System.err.println("Error reading data from the server.");
                        return;
                    }
                    JSONArray jsona = new JSONArray(data);
                    shortcut(jsona);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        task.setActivity(getActivity());

        task.execute(url);
    }


    public void shortcut(JSONArray jsona) throws JSONException {
        data.clear();
        System.out.println("Success??");
        for (int i = 0; i < jsona.length(); i++)
        {
            JSONObject sPoI = jsona.getJSONObject(i);
            int id = sPoI.getInt("id");
            SuggestedPlacesAdapter.SuggestedPoiData spd =
                    new SuggestedPlacesAdapter.SuggestedPoiData(
                            getView(),
                            sPoI.getInt("id"),
                            "https://iseeporto.revtut.net/uploads/PoI_photos/" + sPoI.getInt("id") + ".jpg",
                            stringCrop(sPoI.getString("name"), 25),
                            sPoI.getString("address"),
                            (int)(sPoI.getDouble("distance")));
            data.add(spd);
        }
        spAdapter.notifyDataSetChanged();
    }

    public static String stringCrop(String s, int maxChars)
    {
        String s2 = new String(s);
        if (s2.length() > maxChars)
        {
            s2 = s2.substring(0, maxChars - 3);
            s2 += "...";
        }
        return s2;
    }

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
