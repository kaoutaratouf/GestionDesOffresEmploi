package dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Entreprise;
import util.HibernateUtil;

public class EntrepriseDAO {

    // 1. Ajouter une entreprise
    public boolean ajouter(Entreprise entreprise) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entreprise);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // 2. Afficher toutes les entreprises
    public List<Entreprise> lister() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Entreprise", Entreprise.class)
                          .getResultList();
        }
    }

    // 3. Trouver une entreprise par ID
    public Entreprise trouverParId(int id) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Entreprise.class, id);
        }
    }

    // 4. Modifier une entreprise
    public boolean modifier(Entreprise entreprise) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entreprise);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // 5. Supprimer une entreprise
    public boolean supprimer(int id) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int lignes = session.createMutationQuery(
                    "DELETE FROM Entreprise WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
            tx.commit();
            return lignes > 0;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // 6. Authentifier une entreprise
    public Entreprise authentifier(String email, String motDePasse)
            throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Entreprise WHERE email = :email AND motDePasse = :mdp",
                    Entreprise.class)
                .setParameter("email", email)
                .setParameter("mdp", motDePasse)
                .uniqueResult();
        }
    }
}