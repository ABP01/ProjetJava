import java.util.Date;

public class Abonnement {
    private int id;
    private String libelleOffre;
    private Date dureeMois;
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

    public Date getDureeMois() {
        return dureeMois;
    }

    public void setDureeMois(Date dureeMois) {
        this.dureeMois = dureeMois;
    }

    public float getPrixMensuel() {
        return prixMensuel;
    }

    public void setPrixMensuel(float prixMensuel) {
        this.prixMensuel = prixMensuel;
    }
}
