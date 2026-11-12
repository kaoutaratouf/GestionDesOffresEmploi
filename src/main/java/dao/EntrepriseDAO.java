
package dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Entreprise;
import util.HibernateUtil;

public class EntrepriseDAO {

    // 1. Ajouter une entreprise
    public boolean ajouter(Entreprise entreprise) throws SQLException {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(entreprise);

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

    // 2. Afficher toutes les entreprises
    public List<Entreprise> lister() throws SQLException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Entreprise> query = session.createQuery(
                "FROM Entreprise",
                Entreprise.class
            );

            return query.list();
        }
    }

    // 3. Trouver une entreprise par ID
    public Entreprise trouverParId(int id) throws SQLException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Entreprise.class, id);
        }
    }

    // 4. Modifier une entreprise
    public boolean modifier(Entreprise entreprise) throws SQLException {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(entreprise);

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

    // 5. Supprimer une entreprise
    public boolean supprimer(int id) throws SQLException {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Entreprise entreprise = session.get(Entreprise.class, id);

            if (entreprise != null) {
                session.remove(entreprise);
                transaction.commit();
                return true;
            }

            transaction.rollback();
            return false;

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return false;
        }
    }

    // 6. Authentifier une entreprise
    public Entreprise authentifier(String email, String motDePasse)
            throws SQLException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Entreprise> query = session.createQuery(
                "FROM Entreprise WHERE email = :email AND motDePasse = :mdp",
                Entreprise.class
            );

            query.setParameter("email", email);
            query.setParameter("mdp", motDePasse);

            return query.uniqueResult();
        }
    }
}
