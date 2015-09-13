package antonio.iseeporto.uielements;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import antonio.iseeporto.JSONAsyncTask;
import antonio.iseeporto.R;
import antonio.iseeporto.Singleton;
import antonio.iseeporto.SingletonStringId;

public class WriteReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);


        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessUrl("https://iseeporto.revtut.net/api/api.php?action=make_review&id="
                        + SingletonStringId.getInstance().getId()
                        + "&accessToken=" + Singleton.getInstance().getAccessToken().getToken()
                        + "&comment=" +((EditText) WriteReview.this.findViewById(R.id.review)).getText()
                        + "&like=" + (SingletonStringId.getInstance().isLike() ? 1 : 0));
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_poi_result, menu);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    void accessUrl(String url)
    {
        JSONAsyncTask temp = new JSONAsyncTask() {
            @Override
            public void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
        temp.setActivity(this);
        temp.execute(url);
    }

}
