package antonio.iseeporto;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Locale;

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

    //pages
    private final int PERFIL = 1;
    private final int SUGGESTIONS = 2;
    private final int VISITED = 3;
    private final int NEAR_ME = 4;
    private final int FRIENDS = 5;
    private final int QRCODE = 6;

    //fragments
    Perfil perfilFrag;
    Suggestions suggestionsFrag;
    Visited visitedFrag;
    NearMe nearMeFrag;
    Friends friendsFrag;
    Place placeFrag;

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

        fragInit();

        //temporario
        android.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, placeFrag, "Place");
        transaction.commit();
    }

    private void fragInit() {
        suggestionsFrag = new Suggestions();
        placeFrag = new Place();
        perfilFrag = new Perfil();
        visitedFrag = new Visited();
        nearMeFrag = new NearMe();
        friendsFrag = new Friends();
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
        mTitle = getResources().getStringArray(R.array.menu_options)[number-1];
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(mTitle);

        pageHandler(number);
    }

    private void pageHandler(int number) {

        boolean isPlace = false;
        android.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (number)
        {
            case PERFIL:
                transaction.replace(R.id.container, perfilFrag, "Perfil");
                break;
            case SUGGESTIONS:
                transaction.replace(R.id.container, suggestionsFrag, "Suggestions");
                break;
            case VISITED:
                transaction.replace(R.id.container, visitedFrag, "Visited");
                break;
            case NEAR_ME:
                transaction.replace(R.id.container, nearMeFrag, "NearMe");
                break;
            case FRIENDS:
                transaction.replace(R.id.container, friendsFrag, "Friends");
                break;
            case QRCODE:
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

                //abre o mapa
                double latitude = 41.183239; double longitude = -8.601390;
                openNavigation(latitude, longitude);

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }

        onSectionAttached(PERFIL);
    }

    private void openNavigation(double latitude, double longitude) {
        //o ponto de partida é o local onde o utilizador está
        Intent intent1 = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude));
        startActivity(intent1);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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
            View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
