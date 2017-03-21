package models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import ba.sve_mo.fpmoz.zeljko.foodapp.R;


public class AsyncWebRequest extends AsyncTask<String,String,String>
{
    Context ctx;
    public AsyncWebRequest(){}
    public AsyncWebRequest(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        //display progress dialog.

    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder sb = null;
        int response = 0;
        try {
            String adresa = "http://" + this.ctx.getString(R.string.server_ip) + "/narudzbepicaslim/index.php" + params[0];
            URL url = new URL(adresa.replaceAll(" ", "%20"));

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            response = con.getResponseCode();

            if(con.getInputStream().toString().equals("")) return "";
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    protected void onPostExecute(String result) {
    }
}
