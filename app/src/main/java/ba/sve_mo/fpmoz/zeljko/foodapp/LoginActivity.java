package ba.sve_mo.fpmoz.zeljko.foodapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import models.Korisnik;
import models.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText usernameTxt;
    EditText passwordTxt;
    Button btnLogin;
    TextView lblErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(MainActivity.sessionManager == null)
            MainActivity.sessionManager = new SessionManager(getApplicationContext());

        if(MainActivity.sessionManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        btnLogin    = (Button) findViewById(R.id.btnLogin);
        lblErrorMsg = (TextView) findViewById(R.id.lblErrorMsg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "btnlogin", Toast.LENGTH_SHORT).show();
                String username = String.valueOf(usernameTxt.getText());
                String password = String.valueOf(passwordTxt.getText());

                if(!username.equals("")){
                    if(!password.equals("")){
                        Korisnik korisnik = Korisnik.login(getApplicationContext(), username, password);
                        if(korisnik != null){
                            MainActivity.sessionManager.createLoginSession(korisnik.getUsername(), String.valueOf(korisnik.getId()));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            lblErrorMsg.setTextColor(Color.RED);
                            lblErrorMsg.setText("Greska prilikom logiranja!");
                        }
                    }else{
                        lblErrorMsg.setTextColor(Color.RED);
                        lblErrorMsg.setText("Unesite lozinku!");
                    }
                }else{
                    lblErrorMsg.setTextColor(Color.RED);
                    lblErrorMsg.setText("Unesite korisnicko ime!");
                }
            }
        });
    }
}
