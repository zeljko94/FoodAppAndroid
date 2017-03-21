package ba.sve_mo.fpmoz.zeljko.foodapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import Helpers.OdabraniStol;
import models.Artikl;
import models.Racun;
import models.RacunStavka;
import models.StavkeNarudzbeListAdapter;
import models.Stol;

public class UnosNarudzbeActivity extends AppCompatActivity {
    Spinner brojStolaSpinner;
    ArrayAdapter<String> spinnerAdapter;
    ListView stavkeNarudzbeList;
    static StavkeNarudzbeListAdapter stavkeNarudzbeListAdapter;
    FloatingActionButton floatingActionButtonDodajStavku;
    FloatingActionButton floatingActionButtonPosaljiNarudzbu;
    static ArrayList<RacunStavka> stavke;
    static TextView txtTotal;
    static double TOTAL = 0f;

    static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos_narudzbe);
        ctx = getApplicationContext();

        txtTotal = (TextView) findViewById(R.id.txtTotal);
        brojStolaSpinner = (Spinner) findViewById(R.id.brojStolaSpinner);
        stavkeNarudzbeList = (ListView) findViewById(R.id.stavkeNarudzbeList);
        stavke = new ArrayList<>();
        stavkeNarudzbeListAdapter = new StavkeNarudzbeListAdapter(getApplicationContext(), stavke);
        stavkeNarudzbeList.setAdapter(stavkeNarudzbeListAdapter);
        ArrayList<Stol> stolovi = Stol.dohvatiSve(getApplicationContext());

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Stol.stolArrayToStringArray(stolovi));
        brojStolaSpinner.setAdapter(spinnerAdapter);

        brojStolaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)brojStolaSpinner.getSelectedItem();
                Stol odabrani = Stol.dohvatiPrekoBrojStola(getApplicationContext(), Integer.parseInt(selected));

                OdabraniStol.setStol(odabrani);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        floatingActionButtonDodajStavku = (FloatingActionButton) findViewById(R.id.fab_dodaj_pice);
        floatingActionButtonDodajStavku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnosNarudzbeActivity.this, PrikazArtikalaActivity.class);
                intent.putExtra("trenutneStavke", stavke);
                startActivityForResult(intent, 1);
            }
        });

        floatingActionButtonPosaljiNarudzbu = (FloatingActionButton) findViewById(R.id.fab_posalji_narudzbu);
        floatingActionButtonPosaljiNarudzbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stavke.size() > 0){
                    kreirajRacun();
                    Toast.makeText(getApplicationContext(), "Narudzba uspjesno poslana!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UnosNarudzbeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Nije unesena niti jedna stavka racuna!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Bundle b = data.getExtras();
                if(b != null){
                    ArrayList<Artikl> artikli = (ArrayList<Artikl>) b.getSerializable("stavkeLista");
                    kreirajStavke(artikli);
                }
            }
        }
    }

    public void kreirajStavke(ArrayList<Artikl> odabrani){
        stavke.clear();
        for(Artikl a : odabrani)
        {
            if(a.getNarucenih() > 0) {
                RacunStavka rs = new RacunStavka(a.getNarucenih(), a.getId(), 0, a.getNarucenih() * a.getCijena());
                stavke.add(rs);
            }
        }
        stavkeNarudzbeListAdapter.notifyDataSetChanged();
        updateTotalTxt();
    }

    public static void updateTotalTxt(){
        TOTAL = 0f;
        for(RacunStavka rs : stavke){
            TOTAL += rs.getCijenaStavke();
        }
        txtTotal.setText("TOTAL: " + TOTAL + " KM");
    }

    public static void updateTotalDodanArtikl(double cijena){
        TOTAL += cijena;
        txtTotal.setText("TOTAL: " + TOTAL + " KM");
    }

    public static void updateTotalUklonjenArtikl(double cijena){
        TOTAL -= cijena;
        txtTotal.setText("TOTAL: " + TOTAL + " KM");
    }

    public static void ukloniArtiklPrekoNaziva(Context ctx, String naziv){
        for(int i=0; i<stavke.size(); i++){
            if(stavke.get(i).getNazivStavke(ctx).equals(naziv)){
                stavke.remove(stavke.get(i));
            }
        }
        stavkeNarudzbeListAdapter.notifyDataSetChanged();

    }

    public static void kreirajRacun(){
        String sifraRacuna = UUID.randomUUID().toString();
        Timestamp vrijeme = new Timestamp(System.currentTimeMillis());
        double total = UnosNarudzbeActivity.TOTAL;
        int idKorisnika = 1;
        int idStola = OdabraniStol.getStol() != null ? OdabraniStol.getStol().getId() : 0;
        boolean notifikacija = false;
        boolean jeLiPotvrden = false;

        Racun racun = new Racun(sifraRacuna, vrijeme, total, idKorisnika, idStola, notifikacija, jeLiPotvrden);
        racun.unesi(ctx);
        Racun r = Racun.dohvatiPrekoSifre(ctx, racun.getSifraRacuna());

        for(RacunStavka rs : stavke){
            rs.setIdRacuna(r.getId());
            rs.unesi(ctx);
        }
    }
}
