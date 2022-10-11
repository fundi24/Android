package com.example.projetaout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddObject extends AppCompatActivity {

    private User user;

    //but Wizard
    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           Intent intent = new Intent(AddObject.this, ChooseType.class);
           startActivityForResult(intent,1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                String choose = data.getStringExtra("Type");
                EditText Type = findViewById(R.id.Type);
                Type.setText(choose);
            }
        }
    }

    //but Ajouter
    private View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText nom, type, prix, description;

            nom = findViewById(R.id.Nom);
            type = findViewById(R.id.Type);
            prix = findViewById(R.id.Prix);
            description = findViewById(R.id.Descriptif);

            String Nom = nom.getText().toString();
            String Type = type.getText().toString();
            String Prix = prix.getText().toString();
            String Description = description.getText().toString();

            Intent GetInt = getIntent();
            user =  (User)GetInt.getSerializableExtra("user");
            int userId = user.GetId();


            if(Nom.equals("") || Nom.isEmpty())
            {
                Toast.makeText(AddObject.this,getResources().getString(R.string.ToastNameError), Toast.LENGTH_LONG).show();
            }
            else if(Type.equals("") || Type.isEmpty())
            {
                Toast.makeText(AddObject.this,getResources().getString(R.string.ToastTypeError), Toast.LENGTH_LONG).show();
            }
            else if(Prix.equals("") || Prix.isEmpty() || Double.parseDouble(Prix) <= 0 || isNumeric(Prix) == false)
            {
                Toast.makeText(AddObject.this,getResources().getString(R.string.ToastPriceError), Toast.LENGTH_LONG).show();
            }
            else if(Description.equals("") || Description.isEmpty())
            {
                Toast.makeText(AddObject.this,getResources().getString(R.string.ToastDescriptionError), Toast.LENGTH_LONG).show();
            }
            else {
                new AddObjectAsync(AddObject.this,Nom,Type,Prix,Description,userId).execute();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);

        Button ChooseType = findViewById(R.id.ChooseType);
        ChooseType.setOnClickListener(listener);

        Button But_Validation = findViewById(R.id.But_Validation2);
        But_Validation.setOnClickListener(listener2);

    }


    public void populate (String code_erreur) {
        if(code_erreur.equals("0"))
        {
            Intent intent = new Intent(AddObject.this, ListingObjets.class);
            intent.putExtra("user", user);
            Toast.makeText(AddObject.this,getResources().getString(R.string.ToastAddObjectSucced), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(AddObject.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(AddObject.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }

    //method
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
}