package com.example.projetaout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Register extends AppCompatActivity {

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            EditText idUtilisateur, motDePasse1, motDePasse2;

            idUtilisateur = findViewById(R.id.IdUtilisateur);
            motDePasse1 = findViewById(R.id.MotDePasse1);
            motDePasse2 = findViewById(R.id.MotDePasse2);

            String MotDePasse1 = motDePasse1.getText().toString();
            String IdUtilisateur = idUtilisateur.getText().toString();
            String MotDePasse2 = motDePasse2.getText().toString();

            if(IdUtilisateur.equals("") || IdUtilisateur.isEmpty() || IdUtilisateur.length()<5)
            {
                Toast.makeText(Activity_Register.this,getResources().getString(R.string.ToastIdError), Toast.LENGTH_LONG).show();
            }
            else if(MotDePasse1.equals("") || MotDePasse1.isEmpty() || MotDePasse1.length() < 6)
            {
                Toast.makeText(Activity_Register.this,getResources().getString(R.string.ToastMotDePasseError), Toast.LENGTH_LONG).show();
            }
            else if(MotDePasse2.equals("") || MotDePasse2.isEmpty() || MotDePasse2.length() < 6)
            {
                Toast.makeText(Activity_Register.this,getResources().getString(R.string.ToastMotDePasseError), Toast.LENGTH_LONG).show();
            }
            else if(!MotDePasse1.equals(MotDePasse2))
            {
                Toast.makeText(Activity_Register.this,getResources().getString(R.string.ToastMotDePasseError2), Toast.LENGTH_LONG).show();
            }
            else {
                new RegisterAsync(Activity_Register.this,IdUtilisateur,MotDePasse1).execute();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button But_Inscription = findViewById(R.id.But_Inscription);
        But_Inscription.setOnClickListener(listener);
    }

    public void populate (String code_erreur) {
        if(code_erreur.equals("0"))
        {
            Intent intent = new Intent(Activity_Register.this, MainActivity.class);
            Toast.makeText(Activity_Register.this,getResources().getString(R.string.ToastRegisterSucced), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(Activity_Register.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(Activity_Register.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }
}