package models;

import java.util.Date;

public class Abonne {
    private int id;
    private String nom;
    private String prenom;
    private Date dateInscription;
    private String numeroTelephone;
    private boolean abonnementActif;
    private Abonnement abonnement;

    public Abonne(int id, String nom, String prenom, Date dateInscription, String numeroTelephone, boolean abonnementActif) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateInscription = dateInscription;
        this.numeroTelephone = numeroTelephone;
        this.abonnementActif = abonnementActif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public boolean getAbonnementActif() {
        return abonnementActif;
    }

    public void setAbonnementActif(boolean abonnementActif) {
        this.abonnementActif = abonnementActif;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
