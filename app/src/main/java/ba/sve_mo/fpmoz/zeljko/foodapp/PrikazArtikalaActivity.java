package ba.sve_mo.fpmoz.zeljko.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Artikl;
import models.ArtikliExpandableListAdapter;
import models.Kategorija;
import models.RacunStavka;

public class PrikazArtikalaActivity extends AppCompatActivity {

    ExpandableListView artikliExpandableListview;
    ArtikliExpandableListAdapter adapter;
    List<Kategorija> menuList_parentItems;
    HashMap<Kategorija, List<Artikl>> menuList_childItems;

    ArrayList<Artikl> odabraniArtikli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_artikala);

        odabraniArtikli = new ArrayList<>();

        artikliExpandableListview = (ExpandableListView) findViewById(R.id.artikliExpandableList);
        menuList_parentItems = new ArrayList<>();
        menuList_childItems = new HashMap<>();

        adapter = new ArtikliExpandableListAdapter(getApplicationContext(), menuList_parentItems, menuList_childItems);
        artikliExpandableListview.setAdapter(adapter);

        Intent previous = getIntent();
        Bundle b = previous.getExtras();
        ArrayList<RacunStavka> trenutneStavke = (ArrayList<RacunStavka>) b.getSerializable("trenutneStavke");
        if(trenutneStavke == null){
            trenutneStavke = (ArrayList<RacunStavka>) b.getSerializable("izmjena_trenutneStavke");
        }
        odabraniArtikli.clear();
        for(RacunStavka rs : trenutneStavke)
        {
            Artikl ar = rs.getArtikl(getApplicationContext());
            ar.setNarucenih(rs.getKolicina());
            odabraniArtikli.add(ar);
        }
        menuList_parentItems.addAll(Kategorija.dohvatiSve(getApplicationContext()));
        adapter.notifyDataSetChanged();
        for(Kategorija k: menuList_parentItems){
            ArrayList<Artikl> artikli = Artikl.dohvatiPrekoIdKategorije(getApplicationContext(), k.getId());
            menuList_childItems.put(k, artikli);
        }

        artikliExpandableListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Artikl odabrani = menuList_childItems.get(menuList_parentItems.get(groupPosition)).get(childPosition);
                boolean vecDodan = false;

                for(int i=0; i<odabraniArtikli.size(); i++){
                    if(odabrani.getNazivArtikla().equals(odabraniArtikli.get(i).getNazivArtikla())){
                        odabraniArtikli.get(i).setNarucenih(odabraniArtikli.get(i).getNarucenih()+1);
                        vecDodan = true;
                        break;
                    }
                }
                if(!vecDodan){
                    odabrani.setNarucenih(1);
                    odabraniArtikli.add(odabrani);
                }
                Toast.makeText(getApplicationContext(), "Stavka dodana", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("stavkeLista", odabraniArtikli);
        setResult(Activity.RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}
