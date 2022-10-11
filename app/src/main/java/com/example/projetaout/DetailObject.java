package com.example.projetaout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailObject extends AppCompatActivity {

    private Object object;
    private User user;

    //ajouter dans objet a suivre
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new AddTrackedObjectAsync(DetailObject.this,object, user).execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_object);

        Button But_Validation3 = findViewById(R.id.But_Validation3);
        But_Validation3.setOnClickListener(listener);

        TextView Nom = findViewById(R.id.NomObjet1);
        TextView Type = findViewById(R.id.TypeObjet1);
        TextView Prix = findViewById(R.id.PrixObjet1);
        TextView Description = findViewById(R.id.DescriptionObjet1);
        TextView Distance = findViewById(R.id.Distance1);
        TextView PseudoVendeur = findViewById(R.id.PseudoVendeur);

        Intent GetInt = getIntent();
        Object object = (Object)GetInt.getSerializableExtra("Objet");
        User user = (User)GetInt.getSerializableExtra("user");
        this.object = object;
        this.user = user;

        Nom.setText(object.GetNom());
        Type.setText(object.GetType());
        Prix.setText(object.GetPrix() + " €");
        Description.setText(object.GetDescription());
        Distance.setText(object.GetDistance()+ " km");
        PseudoVendeur.setText(object.GetPseudoVendeur());
    }

    public void populate (String code_erreur) {
        if(code_erreur.equals("0")) {
            try {
                object = null;
                Intent intent = new Intent(DetailObject.this, ListingObjets.class);
                Toast.makeText(DetailObject.this, getResources().getString(R.string.ToastAddTrackedObjectSucced), Toast.LENGTH_LONG).show();
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            } catch (Exception e)
            {
                System.out.println(e);
            }

        }
        else{
            Toast.makeText(DetailObject.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(DetailObject.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }
}