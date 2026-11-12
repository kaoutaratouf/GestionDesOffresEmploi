
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Entreprise;
import util.Connexion;

public class EntrepriseDAO {

    // 1. Ajouter une entreprise
    public boolean ajouter(Entreprise entreprise) throws SQLException {

        String sql = "INSERT INTO Entreprise "
                   + "(nom, email, mot_de_passe) VALUES (?, ?, ?)";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entreprise.getNom());
            ps.setString(2, entreprise.getEmail());
            ps.setString(3, entreprise.getMotDePasse());

            return ps.executeUpdate() > 0;
        }
    }

    // 2. Afficher toutes les entreprises
    public List<Entreprise> lister() throws SQLException {

        List<Entreprise> entreprises = new ArrayList<>();

        String sql = "SELECT id, nom, email FROM Entreprise";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Entreprise entreprise = new Entreprise();

                entreprise.setId(rs.getInt("id"));
                entreprise.setNom(rs.getString("nom"));
                entreprise.setEmail(rs.getString("email"));

                entreprises.add(entreprise);
            }
        }

        return entreprises;
    }

    // 3. Trouver une entreprise par ID
    public Entreprise trouverParId(int id) throws SQLException {

        String sql = "SELECT nom, email, mot_de_passe "
                   + "FROM Entreprise WHERE id = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Entreprise entreprise = new Entreprise();

                    entreprise.setId(id);
                    entreprise.setNom(rs.getString("nom"));
                    entreprise.setEmail(rs.getString("email"));
                    entreprise.setMotDePasse(
                        rs.getString("mot_de_passe")
                    );

                    return entreprise;
                }
            }
        }

        return null;
    }

    // 4. Modifier une entreprise
    public boolean modifier(Entreprise entreprise) throws SQLException {

        String sql = "UPDATE Entreprise SET nom = ?, email = ?, "
                   + "mot_de_passe = ? WHERE id = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entreprise.getNom());
            ps.setString(2, entreprise.getEmail());
            ps.setString(3, entreprise.getMotDePasse());
            ps.setInt(4, entreprise.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // 5. Supprimer une entreprise
    public boolean supprimer(int id) throws SQLException {

        String sql = "DELETE FROM entreprise WHERE id = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    // 6. Authentifier une entreprise
    public Entreprise authentifier(String email, String motDePasse)
            throws SQLException {

        String sql = "SELECT * FROM Entreprise "
                   + "WHERE email = ? AND mot_de_passe = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, motDePasse);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Entreprise(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                    );
                }
            }
        }

        return null;
    }
}
