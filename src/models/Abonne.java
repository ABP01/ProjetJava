import java.util.Date;

public class Abonne {
    private int id;
    private String nom;
    private String prenom;
    private Date dateInscription;
    private Boolean abonnementActif;

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

    public Boolean getAbonnementActif() {
        return abonnementActif;
    }

    public void setAbonnementActif(Boolean abonnementActif) {
        this.abonnementActif = abonnementActif;
    }
}
