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
 * Created by zeljko94 on 31.1.2017..
 */
public class StoloviListViewAdapter extends ArrayAdapter {

    public StoloviListViewAdapter(Context context, ArrayList<Stol> stolovi)
    {
        super(context, R.layout.stol_list_view_redak, stolovi);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.stol_list_view_redak, parent, false);

        Stol stolItem = (Stol) getItem(position);
        ImageView ikonaStola = (ImageView) customView.findViewById(R.id.ikonaStola);
        ikonaStola.setImageResource(R.drawable.stol_ikona);

        TextView brojStolaTxt = (TextView) customView.findViewById(R.id.brojStolaTxt);
        brojStolaTxt.setText(String.valueOf("Stol broj " + (position+1)));
        return customView;
    }
}
