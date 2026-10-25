
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Offre;
import util.Connexion;

public class OffreDAO {

    public List<Offre> afficherToutes() {

        List<Offre> offres = new ArrayList<>();

        String sql = "SELECT * FROM offre";

        try {
            Connection con = Connexion.getConnection();

            System.out.println("Connexion à MySQL réussie");

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Offre offre = new Offre();

                offre.setIdOffre(rs.getInt("id_offre"));
                offre.setTitre(rs.getString("titre"));
                offre.setDescription(rs.getString("description"));
                offre.setEntreprise(rs.getString("entreprise"));
                offre.setLieu(rs.getString("lieu"));
                offre.setTypeContrat(rs.getString("type_contrat"));
                offre.setDatePublication(rs.getDate("date_publication"));
                offre.setDateLimite(rs.getDate("date_limite"));
                offre.setSalaire(rs.getDouble("salaire"));
                offre.setStatut(rs.getString("statut"));

                offres.add(offre);
            }

            System.out.println("Nombre d'offres trouvées : " + offres.size());

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des offres :");
            e.printStackTrace();
        }

        return offres;
    }

public List<Offre> rechercher(String motCle) {

    List<Offre> offres = new ArrayList<>();

    String sql = "SELECT * FROM offre "
               + "WHERE titre LIKE ? OR entreprise LIKE ?";

    try {
        Connection con = Connexion.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "%" + motCle + "%");
        ps.setString(2, "%" + motCle + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Offre offre = new Offre();

            offre.setIdOffre(rs.getInt("id_offre"));
            offre.setTitre(rs.getString("titre"));
            offre.setDescription(rs.getString("description"));
            offre.setEntreprise(rs.getString("entreprise"));
            offre.setLieu(rs.getString("lieu"));
            offre.setTypeContrat(rs.getString("type_contrat"));
            offre.setDatePublication(rs.getDate("date_publication"));
            offre.setDateLimite(rs.getDate("date_limite"));
            offre.setSalaire(rs.getDouble("salaire"));
            offre.setStatut(rs.getString("statut"));

            offres.add(offre);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return offres;
}

}
