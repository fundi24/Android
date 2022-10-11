package com.example.projetaout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrackedObjects extends AppCompatActivity {

    private ListView LV_TrackedObject;
    private User user;
    private double lat;
    private double lon;
    private ArrayList<Object> objects;
    private ArrayAdapter<Object> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracked_objects);

        Intent GetInt = getIntent();
        user = (User)GetInt.getSerializableExtra("user");
        int idUser = user.GetId();
        lat = user.GetLatitude();
        lon = user.GetLongitude();
        new GetTrackedObjectsAsync(TrackedObjects.this, idUser, lat, lon).execute();

    }

    public void populate (String code_erreur, ArrayList<Object> objects) {
        if(code_erreur.equals("0"))
        {
            this.objects = objects;
            LV_TrackedObject = findViewById(R.id.LV_TrackedObject);
            adapter = new ListViewObjetAdapter(this,objects);
            LV_TrackedObject.setAdapter(adapter);
            LV_TrackedObject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object object = (Object) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(getApplicationContext(), DetailObjectTracked.class);
                    intent.putExtra("Objet", object);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            });
            Toast.makeText(TrackedObjects.this,getResources().getString(R.string.ToastObjectsSucced), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(TrackedObjects.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(TrackedObjects.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }
}