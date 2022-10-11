package com.example.projetaout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListingObjets extends AppCompatActivity {

    private User user;
    private double lat;
    private double lon;
    private int IdUser;
    private ListView LV_Objects;
    private ArrayList<Object> objects;
    private ArrayAdapter<Object> adapter;
    private SeekBar JaugeDistance;
    private TextView Distance;
    private int SeekBarDistance;


    //but ajouter un objet
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ListingObjets.this, AddObject.class);
            intent.putExtra("user",user);
            startActivity(intent);
            finish();
        }
    };

    //but liste objets suivis
    private View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ListingObjets.this, TrackedObjects.class);
            intent.putExtra("user",user);
            startActivity(intent);
            finish();
        }
    };

    //but deconnexion
    private View.OnClickListener listener3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            user = null;
            Intent intent = new Intent(ListingObjets.this, MainActivity.class);
            Toast.makeText(ListingObjets.this,getResources().getString(R.string.ToastLogOutSucced), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
    };

    //Obtenir la position actuelle
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            user.SetLongitude(longitude);
            user.SetLatitude(latitude);
            try
            {
                //set la localition en db
                new ListingObjectsAsync(ListingObjets.this, user).execute();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_objets);

        //divers bouton
        Button But_AddObject_Form = findViewById(R.id.But_AddObject_Form);
        But_AddObject_Form.setOnClickListener(listener);

        Button But_ObjetASuivre = findViewById(R.id.But_ObjetASuivre);
        But_ObjetASuivre.setOnClickListener(listener2);

        Button But_Deconnexion = findViewById(R.id.But_Deconnexion);
        But_Deconnexion.setOnClickListener(listener3);

        //recuperer l'objet depuis l'ancienne activité
        Intent GetInt = getIntent();
        user = (User)GetInt.getSerializableExtra("user");
        IdUser = user.GetId();
        lat = user.GetLatitude();
        lon = user.GetLongitude();

        //Recuperer la liste d'objet
        new RecupObjetsAsync(ListingObjets.this, IdUser, lat, lon).execute();


        //Seekbar
        JaugeDistance = findViewById(R.id.JaugeDistance);
        JaugeDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Distance = findViewById(R.id.Distance);
                Distance.setText(" " + String.valueOf(i) + " km");
                SeekBarDistance = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ArrayList<Object> tri = new ArrayList<Object>();
                for(Object o : objects)
                {
                    if(SeekBarDistance >= Integer.parseInt(String.valueOf(o.GetDistance())))
                    {
                        tri.add(o);
                    }
                }
                try {
                    LV_Objects.removeAllViewsInLayout();
                    adapter = new ListViewObjetAdapter(ListingObjets.this, tri);
                    LV_Objects.setAdapter(adapter);
                }catch (Exception e)
                {
                    System.out.println("Problème de chargement");
                }
            }
        });

        //verifier que la permissions a été acceptée
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if(ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED)
        {
            System.out.println("Permission GPS REFUSEE");
        }else{
            //tester si le capteur GPS est activé
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            //Si GPS pas active
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Intent intent= new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }else{
                //Récuperer les coordonnées
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000,
                        10,
                        locationListener
                );
            }

            //Mise en veille ou changement d’orientation pt1
            if(savedInstanceState!=null){
                this.objects = (ArrayList<Object>)savedInstanceState.getSerializable("Objects");
                LV_Objects = findViewById(R.id.LV_Objects);
                adapter = new ListViewObjetAdapter(this,objects);
                LV_Objects.setAdapter(adapter);
            }

        }
    }

    //populate localisation
    public void populate (String code_erreur) {
        if(code_erreur.equals("0"))
        {
            Toast.makeText(ListingObjets.this,getResources().getString(R.string.ToastLocalisationSucced), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ListingObjets.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    //populate affichage des objets
    public void populate2 (String code_erreur, ArrayList<Object> objects) {
        if(code_erreur.equals("0"))
        {
            this.objects = objects;
            LV_Objects = findViewById(R.id.LV_Objects);
            adapter = new ListViewObjetAdapter(this,objects);
            LV_Objects.setAdapter(adapter);
            LV_Objects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object object = (Object) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(getApplicationContext(), DetailObject.class);
                    intent.putExtra("Objet", object);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            });
            Toast.makeText(ListingObjets.this,getResources().getString(R.string.ToastObjectsSucced), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(ListingObjets.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
        }
    }

    //populate erreur general
    public void populate_erreur (String code_erreur)
    {
        Toast.makeText(ListingObjets.this, getResources().getString(R.string.ToastNumError) + code_erreur, Toast.LENGTH_LONG ).show();
    }

    //Mise en veille ou changement d’orientation pt2
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Objects", this.objects);
    }
}