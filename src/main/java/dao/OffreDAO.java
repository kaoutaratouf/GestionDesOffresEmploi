package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import model.Offre;
import util.HibernateUtil;

public class OffreDAO {

    public List<Offre> afficherToutes() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Offre> offres = session.createQuery("FROM Offre", Offre.class)
                                        .getResultList();

            System.out.println("Nombre d'offres trouvées : " + offres.size());
            return offres;

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des offres :");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Offre> rechercher(String motCle) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String motif = "%" + motCle + "%";

            return session.createQuery(
                    "FROM Offre WHERE titre LIKE :motif OR entreprise LIKE :motif",
                    Offre.class)
                .setParameter("motif", motif)
                .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}