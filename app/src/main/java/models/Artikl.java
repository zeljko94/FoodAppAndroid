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
public class Artikl implements Serializable{
    private int id;
    private String sifraArtikla;
    private String nazivArtikla;
    private double cijena;
    private int kolicina;
    private int idKategorije;

    private int narucenih;
    private String slika;

    public Artikl() {
        id = 0;
        sifraArtikla = "";
        nazivArtikla = "";
        cijena = 0f;
        kolicina = 0;
        idKategorije = 0;
        slika = "";
    }

    public Artikl(String sifraArtikla, String nazivArtikla, double cijena, int kolicina, int idKategorije,String slika) {
        this.id = 0;
        this.sifraArtikla = sifraArtikla;
        this.nazivArtikla = nazivArtikla;
        this.cijena = cijena;
        this.kolicina = kolicina;
        this.idKategorije = idKategorije;
        this.slika = slika;
    }

    public static Artikl jsonObjectToArtikl(JSONObject jsonObject)
    {
        Artikl artikl = null;
        try{
            int id = jsonObject.getInt("id");
            String sifraArtikla = jsonObject.getString("sifra_artikla");
            String nazivArtikla = jsonObject.getString("naziv_artikla");
            double cijena = jsonObject.getDouble("cijena");
            int kolicina  = jsonObject.getInt("kolicina");
            int idKategorije = jsonObject.getInt("id_kategorije");
            String slika = jsonObject.getString("slika");

            artikl = new Artikl(sifraArtikla, nazivArtikla, cijena, kolicina, idKategorije, slika);
            artikl.setId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return artikl;
    }

    public static ArrayList<Artikl> jsonArrayToArtiklArray(JSONArray jsonArray)
    {
        ArrayList<Artikl> artikli = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++)
            {
                Artikl artikl = Artikl.jsonObjectToArtikl((JSONObject) jsonArray.get(i));
                artikli.add(artikl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return artikli;
    }

    public static ArrayList<Artikl> dohvatiPrekoIdKategorije(Context ctx, int idKategorije)
    {
        ArrayList<Artikl> artikli = new ArrayList<>();
        try {
            String response = new AsyncWebRequest(ctx).execute("/artikl/kategorija/" + idKategorije).get();
            JSONArray jsonArray = new JSONArray(response);
            artikli.addAll(jsonArrayToArtiklArray(jsonArray));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return artikli;
    }

    public static Artikl dohvatiPrekoId(Context ctx, int id){
        Artikl artikl = null;
        try {
            String response = new AsyncWebRequest(ctx).execute("/artikl/" + id).get();
            JSONArray jsonArray = new JSONArray(response);
            artikl = jsonArrayToArtiklArray(jsonArray).get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return artikl;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSifraArtikla() {
        return sifraArtikla;
    }

    public void setSifraArtikla(String sifraArtikla) {
        this.sifraArtikla = sifraArtikla;
    }

    public String getNazivArtikla() {
        return nazivArtikla;
    }

    public void setNazivArtikla(String nazivArtikla) {
        this.nazivArtikla = nazivArtikla;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getIdKategorije() {
        return idKategorije;
    }

    public void setIdKategorije(int idKategorije) {
        this.idKategorije = idKategorije;
    }

    public int getNarucenih(){
        return this.narucenih;
    }

    public void setNarucenih(int narucenih){
        this.narucenih = narucenih;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}
