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
public class RacunStavka implements Serializable {
    private int id;
    private int kolicina;
    private int idArtikla;
    private int idRacuna;
    private double cijenaStavke;

    public RacunStavka() {
        id = 0;
        kolicina = 0;
        idArtikla = 0;
        idRacuna = 0;
        cijenaStavke = 0f;
    }

    public RacunStavka(int kolicina, int idArtikla, int idRacuna, double cijenaStavke) {
        this.id = 0;
        this.kolicina = kolicina;
        this.idArtikla = idArtikla;
        this.idRacuna = idRacuna;
        this.cijenaStavke = cijenaStavke;
    }

    public static RacunStavka jsonObjectToRacunStavka(Context ctx, JSONObject jsonObject){
        RacunStavka stavka = null;
        try {
            int id = jsonObject.getInt("id");
            int kolicina = jsonObject.getInt("kolicina");
            int idArtikla = jsonObject.getInt("id_artikla");
            int idRacuna  = jsonObject.getInt("id_racuna");
            double cijenaStavke = jsonObject.getDouble("cijena_stavke");
            stavka = new RacunStavka(kolicina,idArtikla,idRacuna,cijenaStavke);
            stavka.setId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stavka;
    }

    public static ArrayList<RacunStavka> jsonArrayToRacunStavkaArray(Context ctx, JSONArray jsonArray){
        ArrayList<RacunStavka> stavke = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                RacunStavka stavka = jsonObjectToRacunStavka(ctx, jsonArray.getJSONObject(i));
                stavke.add(stavka);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stavke;
    }

    public void unesi(Context ctx){
        try {
            String response = new AsyncWebRequest(ctx).execute("/racun_stavke/unesi/" + this.kolicina + "/" +
                                                               this.idArtikla + "/" +
                                                               this.idRacuna  + "/" +
                                                               this.cijenaStavke).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<RacunStavka> dohvatiPrekoRacunId(Context ctx, int id){
        ArrayList<RacunStavka> stavke = new ArrayList<>();
        try {
            String response = new AsyncWebRequest(ctx).execute("/racun_stavke/racunId/" + id).get();
            JSONArray jsonArray = new JSONArray(response);
            stavke.addAll(jsonArrayToRacunStavkaArray(ctx, jsonArray));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stavke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
    }

    public int getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(int idRacuna) {
        this.idRacuna = idRacuna;
    }

    public double getCijenaStavke() {
        return cijenaStavke;
    }

    public void setCijenaStavke(double cijenaStavke) {
        this.cijenaStavke = cijenaStavke;
    }


    public String getNazivStavke(Context ctx){
        return Artikl.dohvatiPrekoId(ctx, this.idArtikla).getNazivArtikla();
    }

    public Artikl getArtikl(Context ctx){
        return Artikl.dohvatiPrekoId(ctx, idArtikla);
    }
}
