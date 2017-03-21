package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ba.sve_mo.fpmoz.zeljko.foodapp.R;

/**
 * Created by zeljko94 on 3.2.2017..
 */
public class ArtikliExpandableListAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private List<Kategorija> parentItems;
    private HashMap<Kategorija, List<Artikl>> childItems;

    public ArtikliExpandableListAdapter(Context ctx, List<Kategorija> parentItems, HashMap<Kategorija, List<Artikl>> childItems)
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
            convertView = inflater.inflate(R.layout.expandable_list_artikl_child, null);
        }

        TextView nazivArtiklaTxt = (TextView) convertView.findViewById(R.id.artiklNazivTxt);
        nazivArtiklaTxt.setText(artikl.getNazivArtikla());
        ImageView artiklIkona = (ImageView) convertView.findViewById(R.id.artiklIkona);
        int imageId = this.ctx.getResources().getIdentifier("drawable/" + artikl.getSlika(), null, this.ctx.getPackageName());
        artiklIkona.setImageResource(imageId);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
