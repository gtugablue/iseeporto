package antonio.iseeporto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchPoi extends AppCompatActivity {
    EditText searchBar;
    SuggestedPlacesAdapter adapter;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        view = getCurrentFocus();
        searchBar = (EditText) findViewById(R.id.searchBar);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByName(searchBar.getText().toString());
            }
        });

        ListView searchResult = (ListView) findViewById(R.id.searchResultsList);
        adapter = new SuggestedPlacesAdapter(this);
        searchResult.setAdapter(adapter);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchPoi.this, SearchPoiResult.class);
                SingletonStringId.getInstance().setId(id + "");
                startActivity(intent);
            }
        });


    }

    private void searchByName(String name)
    {
        String url = "https://iseeporto.revtut.net/api/api.php?action=find_pois_by_name&name=" + name;
        System.out.println(url);
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                try {
                    JSONArray array = new JSONArray(data);
                    shortcut(array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        temp.setActivity(this);
        temp.execute(url);
    }

    private void shortcut(JSONArray array) throws JSONException {
        List<SuggestedPlacesAdapter.SuggestedPoiData> data = adapter.getData();
        data.clear();
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject sPoI = array.getJSONObject(i);
            int id = sPoI.getInt("id");
            SuggestedPlacesAdapter.SuggestedPoiData spd =
                    new SuggestedPlacesAdapter.SuggestedPoiData(
                            view,
                            sPoI.getInt("id"),
                            "https://iseeporto.revtut.net/uploads/PoI_photos/" + 1 + ".jpg",
                            SuggestedMenu.stringCrop(sPoI.getString("name"), 25),
                            sPoI.getString("address"),
                            (int)(sPoI.getDouble("distance")));
            data.add(spd);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
