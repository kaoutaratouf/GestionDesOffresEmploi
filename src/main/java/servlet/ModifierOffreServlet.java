
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Connexion;

@WebServlet("/modifierOffre")
public class ModifierOffreServlet extends HttpServlet {

    private String echapper(String texte) {
        if (texte == null) return "";
        return texte.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Identifiant de l'offre invalide.");
            return;
        }

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM offre WHERE id_offre = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
                            "Offre introuvable.");
                    return;
                }

                java.io.PrintWriter out = response.getWriter();                String cp = request.getContextPath();

                out.println("<!DOCTYPE html><html lang='fr'><head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Modifier une offre</title>");
                out.println("</head><body>");
                out.println("<h2>Modifier une offre</h2>");

                out.println("<form action='" + cp
                        + "/modifierOffre' method='post'>");

                out.println("<input type='hidden' name='id' value='" + id + "'>");

                out.println("<label>Titre :</label><br>");
                out.println("<input type='text' name='titre' required value='"
                        + echapper(rs.getString("titre")) + "'><br><br>");

                out.println("<label>Description :</label><br>");
                out.println("<textarea name='description' required>"
                        + echapper(rs.getString("description"))
                        + "</textarea><br><br>");

                out.println("<label>Entreprise :</label><br>");
                out.println("<input type='text' name='entreprise' required value='"
                        + echapper(rs.getString("entreprise")) + "'><br><br>");

                out.println("<label>Lieu :</label><br>");
                out.println("<input type='text' name='lieu' value='"
                        + echapper(rs.getString("lieu")) + "'><br><br>");

                String contrat = rs.getString("type_contrat");

                out.println("<label>Type de contrat :</label><br>");
                out.println("<select name='type_contrat'>");

                String[] contrats = {
                    "CDI", "CDD", "Stage", "Alternance", "Freelance"
                };

                for (String c : contrats) {
                    out.println("<option value='" + c + "' "
                            + (c.equals(contrat) ? "selected" : "")
                            + ">" + c + "</option>");
                }

                out.println("</select><br><br>");

                java.sql.Date dateLimite = rs.getDate("date_limite");

                out.println("<label>Date limite :</label><br>");
                out.println("<input type='date' name='date_limite' value='"
                        + (dateLimite == null ? "" : dateLimite.toString())
                        + "'><br><br>");

                java.math.BigDecimal salaire = rs.getBigDecimal("salaire");

                out.println("<label>Salaire :</label><br>");
                out.println("<input type='number' name='salaire' min='0' "
                        + "step='0.01' value='"
                        + (salaire == null ? "" : salaire.toString())
                        + "'><br><br>");

                out.println("<button type='submit'>Enregistrer les modifications</button>");
                out.println("</form>");

                out.println("<p><a href='" + cp
                        + "/listeOffre'>Retour à la liste</a></p>");

                out.println("</body></html>");
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement de l'offre.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));

            if (id <= 0) throw new NumberFormatException();

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Identifiant invalide.");
            return;
        }

        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        String entreprise = request.getParameter("entreprise");
        String lieu = request.getParameter("lieu");
        String contrat = request.getParameter("type_contrat");
        String dateLimite = request.getParameter("date_limite");
        String salaireTexte = request.getParameter("salaire");

        if (titre == null || titre.isBlank()
                || description == null || description.isBlank()
                || entreprise == null || entreprise.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Veuillez remplir les champs obligatoires.");
            return;
        }

        java.sql.Date date = null;
        java.math.BigDecimal salaire = null;

        try {
            if (dateLimite != null && !dateLimite.isBlank()) {
                date = java.sql.Date.valueOf(dateLimite);
            }

            if (salaireTexte != null && !salaireTexte.isBlank()) {
                salaire = new java.math.BigDecimal(salaireTexte);

                if (salaire.signum() < 0) {
                    throw new IllegalArgumentException();
                }
            }

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Date ou salaire invalide.");
            return;
        }

        String sql = "UPDATE offre SET titre=?, description=?, entreprise=?, "
                + "lieu=?, type_contrat=?, date_limite=?, salaire=? "
                + "WHERE id_offre=?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, titre.trim());
            ps.setString(2, description.trim());
            ps.setString(3, entreprise.trim());
            ps.setString(4, lieu == null || lieu.isBlank() ? null : lieu.trim());
            ps.setString(5, contrat);

            if (date == null) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, date);
            }

            if (salaire == null) {
                ps.setNull(7, Types.DECIMAL);
            } else {
                ps.setBigDecimal(7, salaire);
            }

            ps.setInt(8, id);

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Offre introuvable.");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/listeOffre");

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la modification de l'offre.", e);
        }
    }
}
