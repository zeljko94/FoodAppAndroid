package ba.sve_mo.fpmoz.zeljko.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import models.AktivneNarudzbeAdapter;
import models.Artikl;
import models.ArtikliExpandableListAdapter;
import models.Kategorija;
import models.MenuPrikazExpandableListAdapter;
import models.Racun;
import models.RacunStavka;
import models.SessionManager;
import models.StavkeNarudzbeListAdapter;
import models.Stol;
import models.StoloviListViewAdapter;

public class MainActivity extends AppCompatActivity {
    public static RequestQueue requestQueue;
    public static MainActivity THIS;
    static SessionManager sessionManager;

    static ListView stoloviListView;
    static ArrayList<Stol> stolovi;
    static StoloviListViewAdapter stoloviAdapter;


    static ExpandableListView menuPrikazExpandableList;
    static List<Kategorija> menuList_parentItems;
    static HashMap<Kategorija, List<Artikl>> menuList_childItems;
    static MenuPrikazExpandableListAdapter menuPrikazExpandableListAdapter;


    static ListView aktivneNarudzbeList;
    static ArrayList<ArrayList<RacunStavka>> aktivneStavke;
    static ArrayList<Racun> aktivniRacuni;
    static AktivneNarudzbeAdapter aktivneNarudzbeAdapter;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        if(!sessionManager.isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        THIS = this;

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UnosNarudzbeActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_postavke_profila) {
            Intent intent = new Intent(MainActivity.this, IzmjenaProfilaActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_settings){
            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.action_odjava){
            MainActivity.sessionManager.logoutUser();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
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

        public PlaceholderFragment() {
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.fragment_stolovi, container, false);
                stolovi = Stol.dohvatiSve(getContext());
                stoloviListView = (ListView) rootView.findViewById(R.id.stoloviListView);
                stoloviAdapter  = new StoloviListViewAdapter(getContext(), stolovi);

                stoloviListView.setAdapter(stoloviAdapter);
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                View rootView = inflater.inflate(R.layout.fragment_narudzbe,container, false);
                aktivneNarudzbeList = (ListView) rootView.findViewById(R.id.aktivneNarudzbeList);
                aktivniRacuni = new ArrayList<>();
                aktivneStavke = new ArrayList<>();
                aktivneNarudzbeAdapter = new AktivneNarudzbeAdapter(getContext(), aktivniRacuni);
                aktivneNarudzbeList.setAdapter(aktivneNarudzbeAdapter);

                aktivneNarudzbeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(THIS, IzmjenaNarudzbeActivity.class);
                        startActivity(intent);
                    }
                });

                ArrayList<Racun> aktivniRacuniIzBaze = Racun.dohvatiNepotvrdene(getContext());
                aktivniRacuni.addAll(aktivniRacuniIzBaze);
                aktivneNarudzbeAdapter.notifyDataSetChanged();

                for(int i=0; i<aktivniRacuni.size(); i++){
                    aktivneStavke.add(RacunStavka.dohvatiPrekoRacunId(getContext(), aktivniRacuni.get(i).getId()));
                }

                return rootView;
            }
            else {
                View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
                menuPrikazExpandableList = (ExpandableListView) rootView.findViewById(R.id.menuPrikazExpandableList);
                menuList_parentItems = new ArrayList<>();
                menuList_childItems = new HashMap<>();

                menuPrikazExpandableListAdapter = new MenuPrikazExpandableListAdapter(getContext(), menuList_parentItems, menuList_childItems);
                menuPrikazExpandableList.setAdapter(menuPrikazExpandableListAdapter);

                menuList_parentItems.addAll(Kategorija.dohvatiSve(getActivity()));
                menuPrikazExpandableListAdapter.notifyDataSetChanged();
                for(Kategorija k: menuList_parentItems){
                    ArrayList<Artikl> artikli = Artikl.dohvatiPrekoIdKategorije(getActivity(), k.getId());
                    menuList_childItems.put(k, artikli);
                }
                return rootView;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Stolovi";
                case 1:
                    return "Narudzbe";
                case 2:
                    return "Menu";
            }
            return null;
        }
    }
}
