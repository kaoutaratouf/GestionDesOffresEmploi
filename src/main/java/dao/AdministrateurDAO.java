package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Administrateur;
import util.HibernateUtil;

public class AdministrateurDAO {

    public Administrateur authentifier(String email, String motDePasse) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Administrateur> query = session.createQuery(
                "FROM Administrateur WHERE email = :email AND motDePasse = :mdp",
                Administrateur.class);

            query.setParameter("email", email);
            query.setParameter("mdp", motDePasse);

            return query.uniqueResult();
        }
    }
}