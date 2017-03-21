package models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by zeljko94 on 31.1.2017..
 */
public class Kategorija implements Serializable{
    private int id;
    private String naziv;
    private String slika;

    public Kategorija() {
        this.id = 0;
        this.naziv = "";
        this.slika = "";
    }

    public Kategorija(String naziv,String slika) {
        this.id = 0;
        this.naziv = naziv;
        this.slika = slika;
    }

    public static Kategorija jsonObjectToKategorija(JSONObject jsonObject)
    {
        Kategorija kategorija = null;
        try {
            int id = jsonObject.getInt("id");
            String naziv = jsonObject.getString("naziv");
            String slika = jsonObject.getString("slika");
            kategorija = new Kategorija(naziv,slika);
            kategorija.setId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kategorija;
    }

    public static ArrayList<Kategorija> jsonArrayToKategorijaArray(JSONArray jsonArray)
    {
        ArrayList<Kategorija> kategorije = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++)
        {
            try {
                Kategorija kategorija = Kategorija.jsonObjectToKategorija((JSONObject) jsonArray.get(i));
                kategorije.add(kategorija);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return kategorije;
    }


    public static ArrayList<Kategorija> dohvatiSve(Context ctx)
    {
        ArrayList<Kategorija> kategorije = new ArrayList<>();
        String response = null;
        try {
            response = new AsyncWebRequest(ctx).execute("/kategorija").get();
            JSONArray jsonArray = new JSONArray(response);
            kategorije.addAll(jsonArrayToKategorijaArray(jsonArray));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kategorije;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}
