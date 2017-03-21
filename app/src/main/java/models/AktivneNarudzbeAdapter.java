package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ba.sve_mo.fpmoz.zeljko.foodapp.R;

/**
 * Created by zeljko94 on 6.2.2017..
 */
public class AktivneNarudzbeAdapter extends ArrayAdapter {
    public AktivneNarudzbeAdapter(Context context, ArrayList<Racun> racuni)
    {
        super(context, R.layout.aktivna_narudzba_list_item, racuni);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.aktivna_narudzba_list_item, parent, false);

        Racun racun = (Racun) getItem(position);

        ImageView statusIkona = (ImageView) customView.findViewById(R.id.statusIkona);
        TextView brojNarduzbeTxt = (TextView) customView.findViewById(R.id.brojNarduzbeTxt);
        TextView brojStolaTxt = (TextView) customView.findViewById(R.id.brojStolaTxt);
        TextView aktivnaNarudzbaCijenaTxt    = (TextView) customView.findViewById(R.id.aktivnaNarudzbaCijenaTxt);

        statusIkona.setImageResource(R.drawable.in_progress);
        brojNarduzbeTxt.setText(String.valueOf("Narudzba " + Integer.valueOf(position+1)));
        brojStolaTxt.setText(String.valueOf("Broj stola: " + racun.getStol(getContext()).getBrojStola()));
        aktivnaNarudzbaCijenaTxt.setText(String.valueOf("Cijena: " + racun.getUkupanIznos() + "KM"));
        return customView;
    }
}
