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
public class Korisnik implements Serializable {
    private int id;
    private String ime;
    private String prezime;
    private String brojTelefona;
    private String privilegije;
    private String username;
    private String password;

    public Korisnik() {
        id = 0;
        ime = "";
        prezime = "";
        brojTelefona = "";
        privilegije = "";
        username = "";
        password = "";
    }

    public Korisnik(String ime, String prezime, String brojTelefona, String privilegije, String username, String password) {
        this.id = 0;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.privilegije = privilegije;
        this.username = username;
        this.password = password;
    }

    public static Korisnik jsonObjectToKorisnik(JSONObject jsonObject){
        Korisnik korisnik = null;
        try {
            int id = jsonObject.getInt("id");
            String ime = jsonObject.getString("ime");
            String prezime = jsonObject.getString("prezime");
            String broj_telefona = jsonObject.getString("broj_telefona");
            String privilegije = jsonObject.getString("privilegije");
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            korisnik = new Korisnik(ime, prezime, broj_telefona, privilegije, username, password);
            korisnik.setId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return korisnik;
    }

    public static ArrayList<Korisnik> jsonArrayToKorisnikArray(JSONArray jsonArray){
        ArrayList<Korisnik> korisnici = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                Korisnik korisnik = jsonObjectToKorisnik(jsonArray.getJSONObject(i));
                korisnici.add(korisnik);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return korisnici;
    }

    public boolean update(Context ctx){
        boolean rez = true;
        try {
            String response = new AsyncWebRequest(ctx).execute("/korisnik/update/" + id + "/" +
                                                                ime + "/" + prezime + "/"+
                                                                brojTelefona + "/" + username + "/" + password
                                                                + "/" + privilegije).get();
            JSONArray jsonArray = new JSONArray(response);
            if(jsonArray.getJSONObject(0).has("error")){
                rez = false;
            }else{
                rez = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rez;
    }

    public static Korisnik dohvatiPrekoId(Context ctx, int id){
        Korisnik korisnik = null;
        String response = null;
        try {
            response = new AsyncWebRequest(ctx).execute("/korisnik/" + id).get();
            JSONArray jsonArray = new JSONArray(response);
            korisnik = jsonObjectToKorisnik(jsonArray.getJSONObject(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return korisnik;
    }

    public static Korisnik login(Context ctx, String username, String pass){
        Korisnik k = null;
        try {
            String response = new AsyncWebRequest(ctx).execute("/korisnik/login/" + username + "/" + pass).get();
            JSONArray jsonArray = new JSONArray(response);
            if(!jsonArray.getJSONObject(0).has("error")){
                k = jsonObjectToKorisnik(jsonArray.getJSONObject(0));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return k;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getPrivilegije() {
        return privilegije;
    }

    public void setPrivilegije(String privilegije) {
        this.privilegije = privilegije;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
