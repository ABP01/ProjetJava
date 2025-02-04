package models;

import java.util.Date;

public class Souscription {
    private int id;
    private int idAbonne;
    private int idAbonnement;
    private Date dateDebut;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(int idAbonne) {
        this.idAbonne = idAbonne;
    }

    public int getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Souscription(int id, int idAbonne, int idAbonnement, Date dateDebut) {
        this.id = id;
        this.idAbonne = idAbonne;
        this.idAbonnement = idAbonnement;
        this.dateDebut = dateDebut;
    }
}