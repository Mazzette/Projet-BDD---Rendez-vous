package com.company;

public class Consultation {
    private int nbConsultation, prix, indicateurAnxiete;
    private String moyenReglement, dateReglement, heureReglement;
    private BilanConsultation bilan;

    public Consultation(String dateReglement, String heureReglement, int prix, String moyenReglement){
        this.dateReglement = dateReglement;
        this.heureReglement = heureReglement;
        this.prix = prix;
        this.moyenReglement = moyenReglement;
    }

    public Consultation(){
        this.dateReglement = " ";
        this.heureReglement = " ";
        this.prix = 0;
        this.nbConsultation = 0;
    }

    public Consultation(String dateReglement, String heureReglement, int prix, String moyenReglement, int indicateurAnxiete){
        this.heureReglement = dateReglement;
        this.heureReglement = heureReglement;
        this.prix = prix;
        this.moyenReglement = moyenReglement;
        this.indicateurAnxiete = indicateurAnxiete;
        this.nbConsultation = 0;
    }

    public void setBilan(BilanConsultation bilan){this.bilan = bilan;}
    public void setNbConsultation(int nbConsultation){this.nbConsultation = nbConsultation;}
    public void setPrix(int prix){this.prix = prix;}
    public void setDateReglement(String dateReglement){this.dateReglement = dateReglement;}
    public void setHeureReglement(String heureReglement){this.heureReglement = heureReglement;}
    public void setMoyenReglement(String moyenReglement){this.moyenReglement = moyenReglement;}
    public void setIndicateurAnxiete(int indicateurAnxiete){this.indicateurAnxiete = indicateurAnxiete;}

    public int getNbConsultation() {return nbConsultation;}
    public String getDateReglement() {return dateReglement;}
    public String getHeureReglement() {return heureReglement;}
    public int getPrix() {return prix;}
    public String getMoyenReglement() {return moyenReglement;}
    public BilanConsultation getBilan(){return bilan;}
    public int getIndicateurAnxiete(){return indicateurAnxiete;}
}
