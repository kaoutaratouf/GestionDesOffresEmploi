
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Connexion;

@WebServlet("/supprimerOffre")
public class SupprimerOffreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id;

        try {
            id = Integer.parseInt(request.getParameter("id"));

            if (id <= 0) throw new NumberFormatException();

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Identifiant de l'offre invalide.");
            return;
        }

        String sql = "DELETE FROM offre WHERE id_offre = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Offre introuvable ou déjà supprimée.");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/listeOffre");

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la suppression de l'offre.", e);
        }
    }
}
