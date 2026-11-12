
package model;

import jakarta.persistence.*;

@Entity
@Table(name = "entreprise")
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    // Constructeur vide obligatoire pour Hibernate
    public Entreprise() {
    }

    // Constructeur avec paramètres
    public Entreprise(int id, String nom, String email, String mot_de_passe) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = mot_de_passe;
    }

    // Constructeur sans ID
    public Entreprise(String nom, String email, String mot_de_passe) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = mot_de_passe;
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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    // Compatibility with existing code

    public String getMot_de_passe() {
        return motDePasse;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.motDePasse = mot_de_passe;
    }
}
