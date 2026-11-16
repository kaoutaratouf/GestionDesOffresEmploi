
package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Candidature;
import model.Candidat;
import model.Offre;
import util.HibernateUtil;

public class CandidatureDAO {

    // 1. Afficher les candidatures reçues par une entreprise
    public List<Candidature> afficherParEntreprise(String nomEntreprise) {

        List<Candidature> liste = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "SELECT c, ca.nom, ca.prenom, ca.email, o.titre " +
                         "FROM Candidature c, Candidat ca, Offre o " +
                         "WHERE c.idCandidat = ca.idCandidat " +
                         "AND c.idOffre = o.idOffre " +
                         "AND o.entreprise = :entreprise " +
                         "ORDER BY c.dateCandidature DESC";

            List<Object[]> resultats = session.createQuery(hql, Object[].class)
                    .setParameter("entreprise", nomEntreprise)
                    .list();

            for (Object[] resultat : resultats) {

                Candidature c = (Candidature) resultat[0];

                c.setNomCandidat((String) resultat[1]);
                c.setPrenomCandidat((String) resultat[2]);
                c.setEmailCandidat((String) resultat[3]);
                c.setTitreOffre((String) resultat[4]);

                liste.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }



public boolean postuler(int idCandidat, int idOffre) {

    Transaction transaction = null;

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        transaction = session.beginTransaction();

        // Vérifier si l'offre existe
        Offre offre = session.get(Offre.class, idOffre);

        if (offre == null) {
            transaction.rollback();
            return false;
        }

        // Vérifier si le candidat a déjà postulé
        String hql = "SELECT COUNT(c) FROM Candidature c " +
                     "WHERE c.idCandidat = :idCandidat " +
                     "AND c.idOffre = :idOffre";

        Long nombre = session.createQuery(hql, Long.class)
                .setParameter("idCandidat", idCandidat)
                .setParameter("idOffre", idOffre)
                .uniqueResult();

        if (nombre > 0) {
            transaction.rollback();
            return false;
        }

        // Créer une nouvelle candidature
        Candidature candidature = new Candidature(
                idCandidat,
                idOffre,
                "En attente"
        );

        session.persist(candidature);

        transaction.commit();

        return true;

    } catch (Exception e) {

        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }

        e.printStackTrace();
        return false;
    }
}


public List<Candidature> afficherToutes() {

    List<Candidature> liste = new ArrayList<>();

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        String hql = "SELECT c, ca.nom, ca.prenom, ca.email, o.titre, o.entreprise " +
                     "FROM Candidature c, Candidat ca, Offre o " +
                     "WHERE c.idCandidat = ca.idCandidat " +
                     "AND c.idOffre = o.idOffre " +
                     "ORDER BY c.dateCandidature DESC";

        List<Object[]> resultats =
                session.createQuery(hql, Object[].class).list();

        for (Object[] resultat : resultats) {

            Candidature c = (Candidature) resultat[0];

            c.setNomCandidat((String) resultat[1]);
            c.setPrenomCandidat((String) resultat[2]);
            c.setEmailCandidat((String) resultat[3]);
            c.setTitreOffre((String) resultat[4]);
            c.setEntreprise((String) resultat[5]);

            liste.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return liste;
}

public List<Candidature> afficherParOffre(int idOffre,
                                          String nomEntreprise) {

    List<Candidature> liste = new ArrayList<>();

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        String hql = "SELECT c, ca.nom, ca.prenom, ca.email, o.titre " +
                     "FROM Candidature c, Candidat ca, Offre o " +
                     "WHERE c.idCandidat = ca.idCandidat " +
                     "AND c.idOffre = o.idOffre " +
                     "AND o.idOffre = :idOffre " +
                     "AND o.entreprise = :entreprise " +
                     "ORDER BY c.dateCandidature DESC";

        List<Object[]> resultats = session.createQuery(hql, Object[].class)
                .setParameter("idOffre", idOffre)
                .setParameter("entreprise", nomEntreprise)
                .list();

        for (Object[] resultat : resultats) {

            Candidature c = (Candidature) resultat[0];

            c.setNomCandidat((String) resultat[1]);
            c.setPrenomCandidat((String) resultat[2]);
            c.setEmailCandidat((String) resultat[3]);
            c.setTitreOffre((String) resultat[4]);

            liste.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return liste;
}


    // 2. Afficher les candidatures d'un candidat
    public List<Candidature> afficherParCandidat(int idCandidat) {

        List<Candidature> liste = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "SELECT c, o.titre, o.entreprise " +
                         "FROM Candidature c, Offre o " +
                         "WHERE c.idOffre = o.idOffre " +
                         "AND c.idCandidat = :id " +
                         "ORDER BY c.dateCandidature DESC";

            List<Object[]> resultats = session.createQuery(hql, Object[].class)
                    .setParameter("id", idCandidat)
                    .list();

            for (Object[] resultat : resultats) {

                Candidature c = (Candidature) resultat[0];

                c.setTitreOffre((String) resultat[1]);
                c.setEntreprise((String) resultat[2]);

                liste.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }


    // 3. Accepter ou refuser une candidature
    // Vérification : l'offre doit appartenir à l'entreprise connectée
    public boolean changerStatut(int idCandidature,
                                 String nouveauStatut,
                                 String nomEntreprise) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Candidature candidature = session.get(
                    Candidature.class, idCandidature);

            if (candidature == null) {
                transaction.rollback();
                return false;
            }

            Offre offre = session.get(
                    Offre.class, candidature.getIdOffre());

            if (offre == null ||
                !offre.getEntreprise().equals(nomEntreprise)) {

                transaction.rollback();
                return false;
            }

            candidature.setStatut(nouveauStatut);

            session.merge(candidature);

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return false;
        }
    }
}
