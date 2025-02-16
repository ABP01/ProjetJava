package models;

public class Abonne {
    private int id;
    private String nom;
    private String prenom;
    private String dateInscription;
    private String numeroTelephone;
    private boolean abonnementActif;
    private String statutSouscription;
    private Abonnement abonnement; // Ajout du champ abonnement

    public Abonne() {
        // Constructeur par défaut
    }

    public Abonne(int id, String nom, String prenom, String dateInscription, String numeroTelephone,
            boolean abonnementActif) {
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

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
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

    public String getStatutSouscription() {
        return statutSouscription;
    }

    public void setStatutSouscription(String statutSouscription) {
        this.statutSouscription = statutSouscription;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
