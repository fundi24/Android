package com.example.projetaout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Activity_Register.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText idUtilisateur, motDePasse;

            idUtilisateur = findViewById(R.id.IdUtilisateur1);
            motDePasse = findViewById(R.id.MotDePasse);

            String IdUtilisateur = idUtilisateur.getText().toString();
            String MotDePasse = motDePasse.getText().toString();
            User user = new User(IdUtilisateur,MotDePasse);

            new MainAsync(MainActivity.this, user).execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button But_Register_Form = findViewById(R.id.But_Register_Form);
        But_Register_Form.setOnClickListener(listener);

        Button But_Connexion = findViewById(R.id.But_Connexion);
        But_Connexion.setOnClickListener(listener2);

    }

    public void populate (String code_erreur, User user) {
        if(code_erreur.equals("0"))
        {
            try{
                Intent intent = new Intent(MainActivity.this, ListingObjets.class);
                intent.putExtra("user", user);
                Toast.makeText(MainActivity.this,getResources().getString(R.string.ToastLoginSucced), Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else{
            Toast.makeText(MainActivity.this, getResources().getString(R.string.ToastLoginError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(MainActivity.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }
}