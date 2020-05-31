package com.company;

public class Profession {
    private String nom, date;
    private int nbPatient;

    public Profession(String nom, String date, int nbPatient){
        this.nom = nom;
        this.date = date;
        this.nbPatient = nbPatient;
    }

    public Profession(int nbPatient, String nom){
        this.nbPatient = nbPatient;
        this.nom = nom;
    }

    public Profession(){
        nom = "Professeur";
        date = "2015-01-09";
        nbPatient = 0;
    }

    public void setDate(String date) {this.date = date;}
    public void setNbPatient(int nbPatient) {this.nbPatient = nbPatient;}
    public void setNom(String nom) { this.nom = nom; }

    public int getNbPatient() {return nbPatient;}
    public String getDate() {return date;}
    public String getNom() {return nom;}
}
