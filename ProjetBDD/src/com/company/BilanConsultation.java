package com.company;

public class BilanConsultation {
    private String motCle, posture, comportement;
    private int nbConsultation;

    public BilanConsultation (String motCle, String posture, String comportement, int nbConsultation){
        this.motCle = motCle;
        this.posture = posture;
        this.comportement = comportement;
        this.nbConsultation = nbConsultation;
    }

    public void setMotCle(String motCle){this.motCle = motCle;}
    public void setPosture (String posture){this.posture = posture;}
    public void setComportement (String comportement){this.comportement = comportement;}
    public void setNbConsultation (int nbConsultation){this.nbConsultation = nbConsultation;}

    public String getMotCle(){return motCle;}
    public String getPosture(){return posture;}
    public String getComportement(){return comportement;}
}
