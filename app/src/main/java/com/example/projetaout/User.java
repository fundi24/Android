package com.example.projetaout;

import android.icu.lang.UProperty;

import java.io.Serializable;

public class User implements Serializable {
    private int Id;
    private String IdUtilisateur;
    private String MotDePasse;
    private double Latitude;
    private double Longitude;


    public User(String IdUtilisateur, String MotDePasse)
    {
        this.IdUtilisateur = IdUtilisateur;
        this.MotDePasse = MotDePasse;
    }

    public void SetId(int id)
    {
        this.Id = id;
    }

    public int GetId()
    {
        return Id;
    }

    public String GetIdUtilisateur ()
    {
        return IdUtilisateur;
    }

    public String GetMotDePasse ()
    {
        return MotDePasse;
    }

    public void SetLatitude(double latitude)
    {
        this.Latitude = latitude;
    }

    public double GetLatitude()
    {
        return Latitude;
    }

    public double GetLongitude()
    {
        return Longitude;
    }

    public void SetLongitude(double longitude)
    {
        this.Longitude = longitude;
    }

}
