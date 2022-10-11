package com.example.projetaout;

import android.os.AsyncTask;

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
import java.util.Scanner;

public class MainAsync extends AsyncTask<Void, Void, String> {

    private MainActivity activity;
    private User user;

    public MainAsync(MainActivity activity, User user)
    {
        this.activity = activity;
        this.user = user;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String IdUtilisateur = user.GetIdUtilisateur();
            String MotDePasse = user.GetMotDePasse();
            String url_base = "http://10.0.2.2/Projet";
            String rpc = "/login.php";
            URL url = new URL(url_base + rpc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String parametres = "IdUtilisateur="+IdUtilisateur+"&MotDePasse="+MotDePasse;
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
                JSONObject jsonObject = new JSONObject(chaineJson);
                String code_erreur = jsonObject.getString("code_erreur");
                int id = jsonObject.getInt("Id");
                user.SetId(id);
                this.activity.populate(code_erreur, user);
            } catch (JSONException e) {
                e.printStackTrace();
                this.activity.populate_erreur("4");
            }
        }
    }
}