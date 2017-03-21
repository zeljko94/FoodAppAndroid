package models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by zeljko94 on 31.1.2017..
 */
public class Racun implements Serializable {
    private int id;
    private String sifraRacuna;
    private Timestamp datumVrijeme;
    private double ukupanIznos;
    private int idKonobara;
    private int idStola;
    private boolean notifikacija;
    private boolean jeLiPotvrden;

    public Racun() {
        id = 0;
        sifraRacuna = "";
        datumVrijeme = null;
        ukupanIznos = 0f;
        idKonobara = 0;
        idStola = 0;
        notifikacija = true;
        this.jeLiPotvrden = false;
    }

    public Racun(String sifraRacuna, Timestamp datumVrijeme, double ukupanIznos, int idKonobara, int idStola, boolean notifikacija, boolean jeLiPotvrden) {
        this.id = 0;
        this.sifraRacuna = sifraRacuna;
        this.datumVrijeme = datumVrijeme;
        this.ukupanIznos = ukupanIznos;
        this.idKonobara = idKonobara;
        this.idStola = idStola;
        this.notifikacija = notifikacija;
        this.jeLiPotvrden = jeLiPotvrden;
    }

    public static Racun jsonObjectToRacun(JSONObject jsonObject){
        Racun r = null;
        try {
            int id = jsonObject.getInt("id");
            String sifra = jsonObject.getString("sifra_racuna");
            String datumStr = jsonObject.getString("datum_vrijeme");
            double total = jsonObject.getDouble("ukupan_iznos");
            int idKorisnika = jsonObject.getInt("id_konobara");
            int idStola     = jsonObject.getInt("id_stola");
            int notifikacijaInt = jsonObject.getInt("notifikacija");
            int jeLiPotvrdenInt = jsonObject.getInt("jeLiPotvrden");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(datumStr);
            Timestamp datum = new java.sql.Timestamp(parsedDate.getTime());

            boolean notifikacija = notifikacijaInt == 1 ? true : false;
            boolean jeLiPotvrden = jeLiPotvrdenInt == 1 ? true : false;
            r = new Racun(sifra,datum,total,idKorisnika,idStola,notifikacija,jeLiPotvrden);
            r.setId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return r;
    }

    public static ArrayList<Racun> jsonArrayToRacunArray(JSONArray jsonArray){
        ArrayList<Racun> racuni = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                Racun r = jsonObjectToRacun(jsonArray.getJSONObject(i));
                racuni.add(r);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return racuni;
    }

    public static ArrayList<Racun> dohvatiNepotvrdene(Context ctx){
        ArrayList<Racun> racuni = new ArrayList<>();
        try {
            String response = new AsyncWebRequest(ctx).execute("/racun/potvrden/0").get();
            JSONArray jsonArray = new JSONArray(response);
            racuni.addAll(jsonArrayToRacunArray(jsonArray));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return racuni;
    }


    public void unesi(Context ctx){
        try {
            String response = new AsyncWebRequest(ctx).execute("/racun/unesi/" + this.sifraRacuna + "/" +
                                                                this.ukupanIznos  + "/" +
                                                                this.idKonobara   + "/" +
                                                                this.idStola      + "/" +
                                                                this.notifikacija).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static Racun dohvatiPrekoSifre(Context ctx, String sifra){
        Racun racun = null;
        try {
            String response = new AsyncWebRequest(ctx).execute("/racun/sifra/" + sifra).get();
            JSONArray jsonArray = new JSONArray(response);
            racun = jsonObjectToRacun(jsonArray.getJSONObject(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return racun;
    }

    public Stol getStol(Context ctx){
        return Stol.dohvatiPrekoId(ctx, this.idStola);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSifraRacuna() {
        return sifraRacuna;
    }

    public void setSifraRacuna(String sifraRacuna) {
        this.sifraRacuna = sifraRacuna;
    }

    public Timestamp getDatumVrijeme() {
        return datumVrijeme;
    }

    public void setDatumVrijeme(Timestamp datumVrijeme) {
        this.datumVrijeme = datumVrijeme;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public int getIdKonobara() {
        return idKonobara;
    }

    public void setIdKonobara(int idKonobara) {
        this.idKonobara = idKonobara;
    }

    public int getIdStola() {
        return idStola;
    }

    public void setIdStola(int idStola) {
        this.idStola = idStola;
    }

    public boolean isNotifikacija() {
        return notifikacija;
    }

    public void setNotifikacija(boolean notifikacija) {
        this.notifikacija = notifikacija;
    }

    public boolean isJeLiPotvrden() {
        return jeLiPotvrden;
    }

    public void setJeLiPotvrden(boolean jeLiPotvrden) {
        this.jeLiPotvrden = jeLiPotvrden;
    }
}
