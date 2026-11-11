package model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "offre")
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offre")
    private int idOffre;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "entreprise")
    private String entreprise;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "type_contrat")
    private String typeContrat;

    @Column(name = "date_publication")
    private Date datePublication;

    @Column(name = "date_limite")
    private Date dateLimite;

    @Column(name = "salaire")
    private double salaire;

    @Column(name = "statut")
    private String statut;

    // Constructeur vide
    public Offre() {
    }

    public int getIdOffre() { return idOffre; }
    public void setIdOffre(int idOffre) { this.idOffre = idOffre; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEntreprise() { return entreprise; }
    public void setEntreprise(String entreprise) { this.entreprise = entreprise; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getTypeContrat() { return typeContrat; }
    public void setTypeContrat(String typeContrat) { this.typeContrat = typeContrat; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public Date getDateLimite() { return dateLimite; }
    public void setDateLimite(Date dateLimite) { this.dateLimite = dateLimite; }

    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}