package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Candidat;
import util.HibernateUtil;

public class CandidatDAO {

    // Ajouter un candidat
    public boolean inscrire(Candidat c) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(c);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Chercher un candidat par email
    public Candidat trouverParEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Candidat WHERE email = :email", Candidat.class)
                .setParameter("email", email)
                .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Connexion
    public Candidat seConnecter(String email, String motDePasse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Candidat WHERE email = :email AND motDePasse = :mdp",
                    Candidat.class)
                .setParameter("email", email)
                .setParameter("mdp", motDePasse)
                .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}