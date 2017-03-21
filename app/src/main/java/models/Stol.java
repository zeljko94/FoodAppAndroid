package models;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by zeljko94 on 31.1.2017..
 */
public class Stol implements Serializable {
    private int id;
    private int brojStola;

    public Stol() {
        this.id = 0;
        this.brojStola = 0;
    }

    public Stol(int brojStola) {
        this.id = 0;
        this.brojStola = brojStola;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrojStola() {
        return brojStola;
    }

    public void setBrojStola(int brojStola) {
        this.brojStola = brojStola;
    }

    public static Stol jsonObjectToStol(JSONObject jsonObject)
    {
        Stol stol = null;
        try {
            int id = jsonObject.getInt("id");
            int brojStola = jsonObject.getInt("broj_stola");
            stol = new Stol(brojStola);
            stol.setId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stol;
    }
    public static ArrayList<Stol> jsonArrayToStolArray(JSONArray jsonArray)
    {
        ArrayList<Stol> stolovi = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++)
        {
            try {
                Stol stol = jsonObjectToStol(jsonArray.getJSONObject(i));
                stolovi.add(stol);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stolovi;
    }

    public static Stol dohvatiPrekoBrojStola(Context ctx, int brojStola)
    {
        Stol stol = null;
        try {
            String response = new AsyncWebRequest(ctx).execute("/stol/brojStola/" + brojStola).get();
            JSONArray jsonArray = new JSONArray(response);
            stol = jsonArrayToStolArray(jsonArray).get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stol;
    }

    public static Stol dohvatiPrekoId(Context ctx, int id){
        Stol stol = null;
        try {
            String response = new AsyncWebRequest(ctx).execute("/stol/id/" + id).get();
            JSONArray jsonArray = new JSONArray(response);
            stol = jsonObjectToStol(jsonArray.getJSONObject(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stol;
    }
    public static ArrayList<Stol> dohvatiSve(Context ctx){
        ArrayList<Stol> stolovi = new ArrayList<>();
        try {
            String response = new AsyncWebRequest(ctx).execute("/stol").get();
            JSONArray jsonArray = new JSONArray(response);
            stolovi.addAll(jsonArrayToStolArray(jsonArray));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stolovi;
    }

    public static ArrayList<String> stolArrayToStringArray(ArrayList<Stol> stolovi)
    {
        ArrayList<String> rez = new ArrayList<>();
        for(Stol s: stolovi)
        {
            rez.add(String.valueOf(s.getBrojStola()));
        }
        return  rez;
    }
}
