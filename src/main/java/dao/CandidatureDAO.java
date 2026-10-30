
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Candidature;
import util.Connexion;

public class CandidatureDAO {

    // Afficher toutes les candidatures reçues
    public List<Candidature> afficherToutes() {

        List<Candidature> candidatures = new ArrayList<>();

        String sql = "SELECT c.id_candidature, c.id_candidat, c.id_offre, "
                   + "c.date_candidature, c.statut, "
                   + "ca.nom, ca.prenom, ca.email, "
                   + "o.titre, o.entreprise "
                   + "FROM candidature c "
                   + "JOIN candidat ca ON c.id_candidat = ca.id_candidat "
                   + "JOIN offre o ON c.id_offre = o.id_offre "
                   + "ORDER BY c.date_candidature DESC";

        try {
            Connection con = Connexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Candidature candidature = new Candidature();

                candidature.setIdCandidature(
                    rs.getInt("id_candidature"));

                candidature.setIdCandidat(
                    rs.getInt("id_candidat"));

                candidature.setIdOffre(
                    rs.getInt("id_offre"));

                candidature.setDateCandidature(
                    rs.getTimestamp("date_candidature"));

                candidature.setStatut(
                    rs.getString("statut"));

                candidature.setNomCandidat(
                    rs.getString("nom"));

                candidature.setPrenomCandidat(
                    rs.getString("prenom"));

                candidature.setEmailCandidat(
                    rs.getString("email"));

                candidature.setTitreOffre(
                    rs.getString("titre"));

                candidature.setEntreprise(
                    rs.getString("entreprise"));

                candidatures.add(candidature);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return candidatures;
    }

    // Accepter ou refuser une candidature
    public boolean changerStatut(int idCandidature, String statut) {

        if (!statut.equals("Acceptée")
                && !statut.equals("Refusée")) {
            return false;
        }

        String sql = "UPDATE candidature "
                   + "SET statut = ? "
                   + "WHERE id_candidature = ?";

        try {
            Connection con = Connexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, statut);
            ps.setInt(2, idCandidature);

            int resultat = ps.executeUpdate();

            ps.close();
            con.close();

            return resultat > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean postuler(int idCandidat, int idOffre) {

        String sql = "INSERT INTO candidature (id_candidat, id_offre, statut) "
                   + "VALUES (?, ?, 'En attente')";

        try {
            Connection con = Connexion.getConnection();

            // Vérifier si le candidat a déjà postulé à cette offre
            String verification = "SELECT * FROM candidature "
                                + "WHERE id_candidat = ? AND id_offre = ?";

            PreparedStatement psVerif = con.prepareStatement(verification);
            psVerif.setInt(1, idCandidat);
            psVerif.setInt(2, idOffre);

            ResultSet rs = psVerif.executeQuery();

            if (rs.next()) {
                rs.close();
                psVerif.close();
                con.close();
                return false;
            }

            rs.close();
            psVerif.close();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCandidat);
            ps.setInt(2, idOffre);

            int resultat = ps.executeUpdate();

            ps.close();
            con.close();

            return resultat > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<Candidature> afficherParCandidat(int idCandidat) {

        List<Candidature> candidatures = new ArrayList<>();

        String sql = "SELECT c.id_candidature, c.date_candidature, c.statut, "
                   + "o.titre, o.entreprise "
                   + "FROM candidature c "
                   + "JOIN offre o ON c.id_offre = o.id_offre "
                   + "WHERE c.id_candidat = ? "
                   + "ORDER BY c.date_candidature DESC";

        try {
            Connection con = Connexion.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCandidat);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Candidature candidature = new Candidature();

                candidature.setIdCandidature(rs.getInt("id_candidature"));
                candidature.setTitreOffre(rs.getString("titre"));
                candidature.setEntreprise(rs.getString("entreprise"));
                candidature.setDateCandidature(
                    rs.getTimestamp("date_candidature")
                );
                candidature.setStatut(rs.getString("statut"));

                candidatures.add(candidature);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return candidatures;
    }
}
