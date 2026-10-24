
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Candidat;
import util.Connexion;

public class CandidatDAO {

    // Ajouter un candidat
    public boolean inscrire(Candidat c) {
        String sql = "INSERT INTO candidat "
                + "(nom, prenom, email, mot_de_passe, telephone) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = Connexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getNom());
            ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getMotDePasse());
            ps.setString(5, c.getTelephone());

            int resultat = ps.executeUpdate();

            ps.close();
            con.close();

            return resultat > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Chercher un candidat par email
    public Candidat trouverParEmail(String email) {
        String sql = "SELECT * FROM candidat WHERE email = ?";

        try {
            Connection con = Connexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Candidat c = new Candidat(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("telephone")
                );

                c.setIdCandidat(rs.getInt("id_candidat"));

                rs.close();
                ps.close();
                con.close();

                return c;
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public Candidat seConnecter(String email, String motDePasse) {
        Candidat c = null;

        String sql = "SELECT * FROM candidat WHERE email = ? AND mot_de_passe = ?";

        try {
            Connection con = Connexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, motDePasse);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Candidat();
                c.setIdCandidat(rs.getInt("id_candidat"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}
