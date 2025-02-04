package models;

public class Abonnement {
    private int id;
    private String libelleOffre;
    private int dureeMois;
    private float prixMensuel;

    public Abonnement() {
        // Constructeur par défaut
    }

    public Abonnement(int id, String libelleOffre, int dureeMois, float prixMensuel) {
        this.id = id;
        this.libelleOffre = libelleOffre;
        this.dureeMois = dureeMois;
        this.prixMensuel = prixMensuel;
    }

    public int getId() {
        return id;
    }

    public String getLibelleOffre() {
        return libelleOffre;
    }

    public int getDureeMois() {
        return dureeMois;
    }

    public float getPrixMensuel() {
        return prixMensuel;
    }

    public void setLibelleOffre(String libelle) {
        this.libelleOffre = libelle;
    }

    public void setPrixMensuel(float prix) {
        this.prixMensuel = prix;
    }

    public void setDureeMois(int duree) {
        this.dureeMois = duree;
    }
}