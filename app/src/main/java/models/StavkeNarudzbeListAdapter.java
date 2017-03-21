package models;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ba.sve_mo.fpmoz.zeljko.foodapp.R;
import ba.sve_mo.fpmoz.zeljko.foodapp.UnosNarudzbeActivity;

/**
 * Created by zeljko94 on 5.2.2017..
 */
public class StavkeNarudzbeListAdapter extends ArrayAdapter {
    Context ctx;
    public StavkeNarudzbeListAdapter(Context context, ArrayList<RacunStavka> stavke)
    {
        super(context, R.layout.stavke_list_item, stavke);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.stavke_list_item, parent, false);

        final RacunStavka stavka = (RacunStavka) getItem(position);

        ImageView ikonaStavke = (ImageView) customView.findViewById(R.id.ikonaStavke);
        if(ikonaStavke == null) System.out.println("IKONA STAVKE = NULL");
        if(this.ctx == null) System.out.println("CONTEXT = NULL");
        int imageId = this.ctx.getResources().getIdentifier("drawable/" + stavka.getArtikl(ctx).getSlika(), null, this.ctx.getPackageName());
        ikonaStavke.setImageResource(Integer.valueOf(imageId));

        TextView nazivStavkeTxt = (TextView) customView.findViewById(R.id.nazivStavkeTxt);
        nazivStavkeTxt.setText(String.valueOf(stavka.getNazivStavke(getContext())));

        final TextView kolicinaStavkeTxt = (TextView) customView.findViewById(R.id.kolicinaStavkeTxt);
        kolicinaStavkeTxt.setText(String.valueOf("(" + stavka.getKolicina() + ")"));

        final TextView cijenaStavke = (TextView) customView.findViewById(R.id.cijenaStavkeTxt);
        cijenaStavke.setText(String.valueOf("Cijena: " + stavka.getCijenaStavke()));

        Button btnDodaj = (Button) customView.findViewById(R.id.btnDodaj);
        Button btnUkloni = (Button) customView.findViewById(R.id.btnUkloni);

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stavka.setKolicina(stavka.getKolicina()+1);
                cijenaStavke.setText(String.valueOf("Cijena: " + stavka.getKolicina() * stavka.getArtikl(getContext()).getCijena()));
                kolicinaStavkeTxt.setText(String.valueOf("(" + stavka.getKolicina() + ")"));
                UnosNarudzbeActivity.updateTotalDodanArtikl(stavka.getArtikl(getContext()).getCijena());
            }
        });

        btnUkloni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stavka.getKolicina() <= 0){
                    stavka.setKolicina(0);
                    return;
                }
                stavka.setKolicina(stavka.getKolicina()-1);
                cijenaStavke.setText(String.valueOf("Cijena: " + stavka.getKolicina() * stavka.getArtikl(getContext()).getCijena()));
                kolicinaStavkeTxt.setText(String.valueOf("(" + stavka.getKolicina() + ")"));
                UnosNarudzbeActivity.updateTotalUklonjenArtikl(stavka.getArtikl(getContext()).getCijena());
                if(stavka.getKolicina() == 0)
                    UnosNarudzbeActivity.ukloniArtiklPrekoNaziva(getContext(), stavka.getNazivStavke(getContext()));
            }
        });
        return customView;
    }
}
