package model;



import java.sql.Timestamp;

public class Candidat {

    // Attributs correspondant aux colonnes MySQL
    private int idCandidat;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private Timestamp dateInscription;

    // Constructeur vide
    public Candidat() {
    }

    // Constructeur utilisé lors de l'inscription
    public Candidat(String nom, String prenom,
                    String email, String motDePasse,
                    String telephone) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
    }

    // Getters et Setters

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Timestamp getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Timestamp dateInscription) {
        this.dateInscription = dateInscription;
    }
}

