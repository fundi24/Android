package com.example.projetaout;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RecupObjetsAsync extends AsyncTask<Void,Void,String> {

    private ListingObjets activity;
    private int IdUser;
    private double LatUser;
    private double LonUser;
    /*private double DISTANCE_MAX = 100;*/

    public RecupObjetsAsync (ListingObjets activity, int IdUser, double lat, double lon)
    {
        this.activity = activity;
        this.IdUser = IdUser;
        LatUser = lat;
        LonUser = lon;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            String url_base = "http://10.0.2.2/Projet";
            String rpc = "/GetObjects.php";
            URL url = new URL(url_base + rpc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String parametres = "Id="+IdUser;
            bufferedWriter.write(parametres);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                Scanner scanner = new Scanner(inputStreamReader);
                String retour ="";
                scanner.useDelimiter("\n");
                while (scanner.hasNext())
                {
                    retour+=scanner.next();
                }
                return  retour;
            } else {
                return "3";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "1";
        } catch (IOException e) {
            e.printStackTrace();
            return "2";
        }
    }

    protected void onPostExecute(String chaineJson)
    {
        if(chaineJson.equals("1") || chaineJson.equals("2") || chaineJson.equals("3")){
            this.activity.populate_erreur(chaineJson);
        } else{
            try {
                ArrayList<Object> objects = new ArrayList<Object>();
                JSONObject jsonObject = new JSONObject(chaineJson);
                JSONArray nbr = jsonObject.getJSONArray("Objets");
                for(int i=0; i<nbr.length(); i++)
                {
                    JSONObject obj=new JSONObject(nbr.get(i).toString());
                    int IdObjet = obj.getInt("IdObjet");
                    String Nom = obj.getString("Nom");
                    String Type = obj.getString("Type");
                    double Prix = obj.getDouble("Prix");
                    String Description = obj.getString("Descriptif");
                    double Latitude = obj.getDouble("Latitude");
                    double Longitude = obj.getDouble("Longitude");
                    String PseudoVendeur = obj.getString("Identifiant");
                    double DistanceFromUser = Calcule_distance(Latitude,Longitude,LatUser,LonUser);
                    /*if(DistanceFromUser <= DISTANCE_MAX)
                    {
                        Object o = new Object(IdObjet,Nom,Type,Prix,Description,Latitude,Longitude, PseudoVendeur, DistanceFromUser);
                        objects.add(o);
                    }*/
                    Object o = new Object(IdObjet,Nom,Type,Prix,Description,Latitude,Longitude, PseudoVendeur, DistanceFromUser);
                    objects.add(o);
                }
                String code_erreur = jsonObject.getString("code_erreur");

                this.activity.populate2(code_erreur, objects);
            } catch (JSONException e) {
                e.printStackTrace();
                this.activity.populate_erreur("4");
            }
        }
    }

    public double Calcule_distance(double objetLat, double objetLon, double userLat, double userLong)
    {
        double latRad, lonRad, tlatRad, tlonRad, Distance;

        //conversion des valeurs en degree vers le radian
        latRad = objetLat * 0.017453293;
        lonRad = objetLon * 0.017453293;
        tlatRad = userLat * 0.017453293;
        tlonRad = userLong * 0.017453293;

        //calcule de la distance en Km
        double latSin = Math.sin((latRad - tlatRad)/2);
        double lonSin = Math.sin((lonRad - tlonRad)/2);

        Distance = 2 * Math.asin(Math.sqrt(latSin*latSin) + Math.cos(latRad) * Math.cos(tlatRad) * (lonSin * lonSin));
        Distance = Distance * 6378.137;

        return Distance;
    }
}
