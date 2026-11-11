package model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "candidature")
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidature")
    private int idCandidature;

    @Column(name = "id_candidat")
    private int idCandidat;

    @Column(name = "id_offre")
    private int idOffre;

    // Valeur par défaut gérée par MySQL
    @Column(name = "date_candidature", insertable = false, updatable = false)
    private Timestamp dateCandidature;

    @Column(name = "statut")
    private String statut;

    // Champs issus des jointures (pas des colonnes de la table)
    @Transient
    private String nomCandidat;

    @Transient
    private String prenomCandidat;

    @Transient
    private String emailCandidat;

    @Transient
    private String titreOffre;

    @Transient
    private String entreprise;

    public Candidature() {
    }

    public Candidature(int idCandidat, int idOffre, String statut) {
        this.idCandidat = idCandidat;
        this.idOffre = idOffre;
        this.statut = statut;
    }

    public int getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(int idCandidature) {
        this.idCandidature = idCandidature;
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public Timestamp getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(Timestamp dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNomCandidat() {
        return nomCandidat;
    }

    public void setNomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
    }

    public String getPrenomCandidat() {
        return prenomCandidat;
    }

    public void setPrenomCandidat(String prenomCandidat) {
        this.prenomCandidat = prenomCandidat;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }

    public void setEmailCandidat(String emailCandidat) {
        this.emailCandidat = emailCandidat;
    }

    public String getTitreOffre() {
        return titreOffre;
    }

    public void setTitreOffre(String titreOffre) {
        this.titreOffre = titreOffre;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
}