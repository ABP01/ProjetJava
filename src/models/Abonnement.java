package models;


public class Abonnement {
    private int id;
    private String libelleOffre;
    private int dureeMois;
    private float prixMensuel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelleOffre() {
        return libelleOffre;
    }

    public void setLibelleOffre(String libelleOffre) {
        this.libelleOffre = libelleOffre;
    }

    public int getDureeMois() {
        return dureeMois;
    }

    public void setDureeMois(int dureeMois) {
        this.dureeMois = dureeMois;
    }

    public float getPrixMensuel() {
        return prixMensuel;
    }

    public void setPrixMensuel(float prixMensuel) {
        this.prixMensuel = prixMensuel;
    }

    public Abonnement(int id, String libelleOffre, int dureeMois, float prixMensuel) {
        this.id = id;
        this.libelleOffre = libelleOffre;
        this.dureeMois = dureeMois;
        this.prixMensuel = prixMensuel;
    }
}
