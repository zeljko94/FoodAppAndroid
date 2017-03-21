package ba.sve_mo.fpmoz.zeljko.foodapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import models.Korisnik;

public class IzmjenaProfilaActivity extends AppCompatActivity {
    EditText txtIme;
    EditText txtPrezime;
    EditText txtTelefon;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtRePassword;
    Button btnSpremiIzmjene;
    TextView lblErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmjena_profila);

        txtIme = (EditText) findViewById(R.id.txtIme);
        txtPrezime = (EditText) findViewById(R.id.txtPrezime);
        txtTelefon = (EditText) findViewById(R.id.txtTelefon);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRePassword = (EditText) findViewById(R.id.txtRePassword);
        btnSpremiIzmjene = (Button) findViewById(R.id.btnSpremiIzmjene);
        lblErrorMsg = (TextView) findViewById(R.id.lblErrorMsg);

        int idLogiranog = Integer.parseInt(MainActivity.sessionManager.getUserDetails().get("id"));
        final Korisnik logirani = Korisnik.dohvatiPrekoId(getApplicationContext(), idLogiranog);

        txtIme.setText(String.valueOf(logirani.getIme()));
        txtPrezime.setText(String.valueOf(logirani.getPrezime()));
        txtTelefon.setText(String.valueOf(logirani.getBrojTelefona()));
        txtUsername.setText(String.valueOf(logirani.getUsername()));


        btnSpremiIzmjene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime = String.valueOf(txtIme.getText());
                String prezime = String.valueOf(txtPrezime.getText());
                String telefon = String.valueOf(txtTelefon.getText());
                String username = String.valueOf(txtUsername.getText());
                String password = String.valueOf(txtPassword.getText());
                String rePassword = String.valueOf(txtRePassword.getText());

                if(!ime.equals("")){
                    if(!prezime.equals("")){
                        if(!username.equals("")){
                            if(!password.equals("")){
                                if(!rePassword.equals("")){
                                    logirani.setIme(ime);
                                    logirani.setPrezime(prezime);
                                    logirani.setBrojTelefona(telefon);
                                    logirani.setUsername(username);
                                    logirani.setPassword(password);
                                    logirani.setPrivilegije("konobar");
                                    if(logirani.update(getApplicationContext())){
                                        lblErrorMsg.setTextColor(Color.GREEN);
                                        lblErrorMsg.setText("Podaci uspjesno izmjenjeni!");
                                    }else{
                                        lblErrorMsg.setTextColor(Color.RED);
                                        lblErrorMsg.setText("Korisnicko ime je zauzeto!");
                                    }
                                }else{
                                    lblErrorMsg.setTextColor(Color.RED);
                                    lblErrorMsg.setText("Unesite ponovljenu lozinku!");
                                }
                            }else{
                                lblErrorMsg.setTextColor(Color.RED);
                                lblErrorMsg.setText("Unesite lozinku!");
                            }
                        }else{
                            lblErrorMsg.setTextColor(Color.RED);
                            lblErrorMsg.setText("Unesite korisničko ime!");
                        }
                    }else
                    {
                        lblErrorMsg.setTextColor(Color.RED);
                        lblErrorMsg.setText("Unesite prezime!");
                    }
                }else{
                    lblErrorMsg.setTextColor(Color.RED);
                    lblErrorMsg.setText("Unesite ime!");
                }
            }
        });
    }
}
