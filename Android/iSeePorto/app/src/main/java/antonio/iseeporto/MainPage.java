package antonio.iseeporto;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class MainPage extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    protected abstract class MenuOptions{
        public final static int PERFIL = 0;
        public final static int SUGGESTIONS = 1;
        public final static int VISITED = 2;
        public final static int FRIENDS = 3;
        public final static int QRCODE = 4;
    }

    //fragments
    final Perfil perfilFrag = new Perfil();
    final SuggestedMenu suggestionsFrag = new SuggestedMenu();
    final VisitedMenu visitedFrag = new VisitedMenu();
    final FeedMenu friendsFrag = new FeedMenu();
    final Place placeFrag = new Place();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        SingletonStringId.getInstance().setId("1");
    }

    @Override
    protected void onStop() {
        LoginManager.getInstance().logOut();
        super.onStop();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        setMTitle(number);

        pageHandler(number);
    }

    private void setMTitle(int number) {
        mTitle = getResources().getStringArray(R.array.menu_options)[number-1];
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(mTitle);
    }

    private void pageHandler(int number) {

        boolean isPlace = false;
        android.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (number-1)
        {
            case MenuOptions.PERFIL:
                SingletonStringId.getInstance().setId(null);
                transaction.replace(R.id.container, perfilFrag, "Perfil");
                break;
            case MenuOptions.SUGGESTIONS:
                transaction.replace(R.id.container, suggestionsFrag, "Suggestions");
                break;
            case MenuOptions.VISITED:
                transaction.replace(R.id.container, visitedFrag, "Visited");
                break;
            case MenuOptions.FRIENDS:
                //transaction.replace(R.id.container, friendsFrag, "Friends");
                transaction.replace(R.id.container, placeFrag, "Friends");
                break;
            case MenuOptions.QRCODE:
                startQRCode();
                transaction.replace(R.id.container, placeFrag, "Place");
                isPlace = true;
                break;
            default:
                Log.e("Page Error", "Page Index out of bounds!");
                break;
        }

        transaction.commit();

        if (isPlace) {
            placeFrag.createVisitFrag();
        }
    }

    private void startQRCode() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan

            } else {
                if (resultCode == RESULT_CANCELED)
                    Toast.makeText(getApplicationContext(), "QR Code read error!", Toast.LENGTH_SHORT).show();
            }
        }

        onSectionAttached(MenuOptions.PERFIL);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
        //descomentar para aparecer botão no canto superior direito
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main_page, menu);
//            restoreActionBar();
//            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main_page, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



}
