package ba.sve_mo.fpmoz.zeljko.foodapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import models.Artikl;
import models.Racun;
import models.RacunStavka;
import models.StavkeNarudzbeListAdapter;

public class IzmjenaNarudzbeActivity extends AppCompatActivity {
    static double TOTAL = 0f;
    static Racun odabrani;
    static ArrayList<RacunStavka> stavke;
    static ArrayList<RacunStavka> trenutneStavke;

    TextView izmjenaNarudzbe_txtBrojStola;
    static TextView izmjenaNarudzbe_txtTotal;
    Spinner izmjenaNarudzbe_brojStolaSpinner;
    ListView izmjenaNarudzbe_stavkeListView;
    FloatingActionButton izmjenaNarudzbe_fab_dodaj_pice;
    FloatingActionButton izmjenaNarudzbe_fab_posalji_narudzbu;

    static StavkeNarudzbeListAdapter izmjenaNarudzbe_stavkeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmjena_narudzbe);

        izmjenaNarudzbe_txtBrojStola = (TextView) findViewById(R.id.izmjenaNarudzbe_txtBrojStola);
        izmjenaNarudzbe_txtTotal = (TextView) findViewById(R.id.izmjenaNarudzbe_txtTotal);
        izmjenaNarudzbe_brojStolaSpinner = (Spinner) findViewById(R.id.izmjenaNarudzbe_brojStolaSpinner);
        izmjenaNarudzbe_stavkeListView = (ListView) findViewById(R.id.izmjenaNarudzbe_stavkeListView);
        izmjenaNarudzbe_fab_dodaj_pice = (FloatingActionButton) findViewById(R.id.izmjenaNarudzbe_fab_dodaj_pice);
        izmjenaNarudzbe_fab_posalji_narudzbu = (FloatingActionButton) findViewById(R.id.izmjenaNarudzbe_fab_posalji_narudzbu);

        stavke = new ArrayList<>();

        izmjenaNarudzbe_stavkeAdapter = new StavkeNarudzbeListAdapter(this, stavke);
        izmjenaNarudzbe_stavkeListView.setAdapter(izmjenaNarudzbe_stavkeAdapter);

        izmjenaNarudzbe_fab_dodaj_pice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IzmjenaNarudzbeActivity.this, PrikazArtikalaActivity.class);
                intent.putExtra("izmjena_trenutneStavke", stavke);
                startActivityForResult(intent, 2);
            }
        });

        izmjenaNarudzbe_fab_posalji_narudzbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stavke.size() > 0){

                }else{

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                Bundle b = data.getExtras();
                if(b != null){
                    ArrayList<Artikl> artikli = (ArrayList<Artikl>) b.getSerializable("stavkeLista");
                    kreirajStavke(artikli);
                    Toast.makeText(getApplicationContext(), artikli.toString(), Toast.LENGTH_SHORT).show();
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
        izmjenaNarudzbe_stavkeAdapter.notifyDataSetChanged();
        //updateTotalTxt();
    }

}
