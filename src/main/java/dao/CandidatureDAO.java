package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Candidat;
import model.Candidature;
import model.Offre;
import util.HibernateUtil;

public class CandidatureDAO {

    // Afficher toutes les candidatures reçues
    public List<Candidature> afficherToutes() {
        List<Candidature> resultat = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Object[]> lignes = session.createQuery(
                "SELECT c, ca, o FROM Candidature c, Candidat ca, Offre o "
              + "WHERE c.idCandidat = ca.idCandidat "
              + "AND c.idOffre = o.idOffre "
              + "ORDER BY c.dateCandidature DESC",
                Object[].class).getResultList();

            for (Object[] ligne : lignes) {
                Candidature c = (Candidature) ligne[0];
                Candidat ca = (Candidat) ligne[1];
                Offre o = (Offre) ligne[2];

                c.setNomCandidat(ca.getNom());
                c.setPrenomCandidat(ca.getPrenom());
                c.setEmailCandidat(ca.getEmail());
                c.setTitreOffre(o.getTitre());
                c.setEntreprise(o.getEntreprise());

                resultat.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultat;
    }

    // Accepter ou refuser une candidature
    public boolean changerStatut(int idCandidature, String statut) {

        if (!statut.equals("Acceptée") && !statut.equals("Refusée")) {
            return false;
        }

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            int lignes = session.createMutationQuery(
                "UPDATE Candidature SET statut = :statut "
              + "WHERE idCandidature = :id")
                .setParameter("statut", statut)
                .setParameter("id", idCandidature)
                .executeUpdate();

            tx.commit();
            return lignes > 0;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Postuler à une offre
    public boolean postuler(int idCandidat, int idOffre) {

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // Vérifier si le candidat a déjà postulé
            Long deja = session.createQuery(
                "SELECT COUNT(c) FROM Candidature c "
              + "WHERE c.idCandidat = :cand AND c.idOffre = :offre",
                Long.class)
                .setParameter("cand", idCandidat)
                .setParameter("offre", idOffre)
                .getSingleResult();

            if (deja > 0) {
                return false;
            }

            tx = session.beginTransaction();

            Candidature c = new Candidature();
            c.setIdCandidat(idCandidat);
            c.setIdOffre(idOffre);
            c.setStatut("En attente");

            session.persist(c);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Candidatures d'un candidat
    public List<Candidature> afficherParCandidat(int idCandidat) {
        List<Candidature> resultat = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Object[]> lignes = session.createQuery(
                "SELECT c, o FROM Candidature c, Offre o "
              + "WHERE c.idOffre = o.idOffre "
              + "AND c.idCandidat = :id "
              + "ORDER BY c.dateCandidature DESC",
                Object[].class)
                .setParameter("id", idCandidat)
                .getResultList();

            for (Object[] ligne : lignes) {
                Candidature c = (Candidature) ligne[0];
                Offre o = (Offre) ligne[1];

                c.setTitreOffre(o.getTitre());
                c.setEntreprise(o.getEntreprise());

                resultat.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultat;
    }
}