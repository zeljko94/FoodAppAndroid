package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import ba.sve_mo.fpmoz.zeljko.foodapp.R;

/**
 * Created by zeljko94 on 5.2.2017..
 */
public class MenuPrikazExpandableListAdapter extends BaseExpandableListAdapter{
    private Context ctx;
    private List<Kategorija> parentItems;
    private HashMap<Kategorija, List<Artikl>> childItems;

    public MenuPrikazExpandableListAdapter(Context ctx, List<Kategorija> parentItems, HashMap<Kategorija, List<Artikl>> childItems)
    {
        this.ctx = ctx;
        this.parentItems = parentItems;
        this.childItems = childItems;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childItems.get(this.parentItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parentItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childItems.get(this.parentItems.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Kategorija k = (Kategorija) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.artikli_expandable_list_parent_redak, null);
        }

        TextView nazivKategorijeTxt = (TextView) convertView.findViewById(R.id.nazivKategorijeTxt);
        nazivKategorijeTxt.setText(k.getNaziv());

        ImageView ikonaKategorije = (ImageView) convertView.findViewById(R.id.ikonaKategorije);
        int imageId = this.ctx.getResources().getIdentifier("drawable/" + k.getSlika(), null, this.ctx.getPackageName());
        ikonaKategorije.setImageResource(imageId);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Artikl artikl = (Artikl) getChild(groupPosition, childPosition);
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_prikaz_expandable_list_child, null);
        }

        ImageView artiklIkona = (ImageView) convertView.findViewById(R.id.menuArtiklIkona);
        int imageId = this.ctx.getResources().getIdentifier("drawable/" + artikl.getSlika(), null, this.ctx.getPackageName());
        artiklIkona.setImageResource(imageId);

        TextView nazivTxt = (TextView) convertView.findViewById(R.id.nazivTxt);
        nazivTxt.setText(artikl.getNazivArtikla());
        nazivTxt.setSelected(true);

        TextView cijenaTxt = (TextView) convertView.findViewById(R.id.cijenaTxt);
        cijenaTxt.setText("Cijena: "+ round(artikl.getCijena(),2) + " KM");
        cijenaTxt.setSelected(true);

        TextView kolicinaTxt = (TextView) convertView.findViewById(R.id.kolicinaTxt);
        kolicinaTxt.setText("Kolicina: "+ artikl.getKolicina());
        kolicinaTxt.setSelected(true);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
