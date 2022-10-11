package com.example.projetaout;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Object implements Serializable {

    private int Id;
    private String Nom;
    private String Type;
    private double Prix;
    private String Description;
    private double Latitude;
    private double Longitude;
    private String PseudoVendeur;
    private double DistanceFromUser;
    private DecimalFormat df = new DecimalFormat("0.00");

    public Object(int Id, String nom, String type, double prix, String description, double latitude, double longitude, String PseudoVendeur, double DistanceFromUser)
    {
        this.Id = Id;
        Nom = nom;
        Type = type;
        Prix = prix;
        Description = description;
        Latitude = latitude;
        Longitude = longitude;
        this.PseudoVendeur = PseudoVendeur;
        this.DistanceFromUser = DistanceFromUser;
    }

    public void SetId(int id)
    {
        this.Id = id;
    }

    public int GetId()
    {
        return Id;
    }

    public String GetNom() {return Nom;}
    public String GetType() {return Type;}
    public String GetPrix() {
        return df.format(Prix);
    }
    public String GetDescription() {return Description;}
    public String GetPseudoVendeur(){return PseudoVendeur;}
    public String GetDistance(){return df.format(DistanceFromUser);}

}
