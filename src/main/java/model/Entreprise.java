
package model;

public class Entreprise {

    private int id;
    private String nom;
    private String email;
    private String mot_de_passe;

    // Constructeur vide
    public Entreprise() {
    }

    // Constructeur avec paramètres
    public Entreprise(int id, String nom, String email, String mot_de_passe) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    // Constructeur sans ID
    public Entreprise(String nom, String email, String mot_de_passe) {
        this.nom = nom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    // Getters et Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    // Compatibility with EntrepriseDAO and ModifierEntrepriseServlet
    public String getMotDePasse() {
        return mot_de_passe;
    }

    public void setMotDePasse(String motDePasse) {
        this.mot_de_passe = motDePasse;
    }
}
