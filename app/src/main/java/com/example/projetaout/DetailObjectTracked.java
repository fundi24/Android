package com.example.projetaout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailObjectTracked extends AppCompatActivity {

    private Object object;
    private User user;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int objectId = object.GetId();
            int userId = user.GetId();
            new DelObjectAsync(DetailObjectTracked.this,userId, objectId).execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_object_tracked);

        Button But_delete = findViewById(R.id.But_delete);
        But_delete.setOnClickListener(listener);

        TextView Nom = findViewById(R.id.NomObjet2);
        TextView Type = findViewById(R.id.TypeObjet2);
        TextView Prix = findViewById(R.id.PrixObjet2);
        TextView Description = findViewById(R.id.DescriptionObjet2);
        TextView Distance = findViewById(R.id.Distance2);
        TextView PseudoVendeur = findViewById(R.id.PseudoVendeur2);

        Intent GetInt = getIntent();
        object = (Object)GetInt.getSerializableExtra("Objet");
        user = (User)GetInt.getSerializableExtra("user");


        Nom.setText(object.GetNom());
        Type.setText(object.GetType());
        Prix.setText(object.GetPrix() + " €");
        Description.setText(object.GetDescription());
        Distance.setText(object.GetDistance() + " km");
        PseudoVendeur.setText(object.GetPseudoVendeur());
    }

    public void populate (String code_erreur) {
        if(code_erreur.equals("0"))
        {
            object = null;
            Intent intent = new Intent(DetailObjectTracked.this, ListingObjets.class);
            intent.putExtra("user",user);
            Toast.makeText(DetailObjectTracked.this, getResources().getString(R.string.ToastDelObjectSucced), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(DetailObjectTracked.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(DetailObjectTracked.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }
}