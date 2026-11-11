
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Administrateur;
import util.Connexion;

public class AdministrateurDAO {

    public Administrateur authentifier(String email, String motDePasse)
            throws SQLException {

        String sql = "SELECT * FROM Administrateur "
                   + "WHERE email = ? AND mot_de_passe = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, motDePasse);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Administrateur(
                        rs.getString("email"),
                        motDePasse
                    );
                }
            }
        }

        return null;
    }
}
