
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

    private void afficherErreur(HttpServletResponse response,
                                String message) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        java.io.PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='fr'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Erreur</title>");

        out.println("<style>");
        out.println("body { font-family: Arial; background: #f4f7f5; "
                + "margin: 0; padding: 40px; }");
        out.println(".container { max-width: 500px; margin: 60px auto; "
                + "background: white; padding: 30px; border-radius: 10px; "
                + "box-shadow: 0 2px 10px rgba(0,0,0,0.1); text-align: center; }");
        out.println("h2 { color: #c62828; }");
        out.println("a { display: inline-block; margin-top: 15px; "
                + "color: white; background: #2e7d32; padding: 10px 20px; "
                + "text-decoration: none; border-radius: 5px; }");
        out.println("a:hover { background: #1b5e20; }");
        out.println("</style>");

        out.println("</head><body>");
        out.println("<div class='container'>");
        out.println("<h2>Une erreur est survenue</h2>");
        out.println("<p>" + echapper(message) + "</p>");
        out.println("<a href='" + response.encodeURL(
                getServletContext().getContextPath() + "/listeOffre")
                + "'>Retour à la liste des offres</a>");
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        int id;

        try {
            id = Integer.parseInt(request.getParameter("id"));

            if (id <= 0) throw new NumberFormatException();

        } catch (Exception e) {
            afficherErreur(response, "Identifiant de l'offre invalide.");
            return;
        }

        String cp = request.getContextPath();

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM offre WHERE id_offre = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    afficherErreur(response, "Offre introuvable.");
                    return;
                }

                java.io.PrintWriter out = response.getWriter();

                out.println("<!DOCTYPE html>");
                out.println("<html lang='fr'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Modifier une offre</title>");

                out.println("<style>");

                out.println("* { box-sizing: border-box; }");

                out.println("body { font-family: Arial, sans-serif; "
                        + "background: #f4f7f5; color: #333; margin: 0; "
                        + "padding: 30px 15px; }");

                out.println(".container { max-width: 650px; margin: 30px auto; "
                        + "background: white; padding: 30px; "
                        + "border-radius: 10px; "
                        + "box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");

                out.println("h2 { text-align: center; color: #2e7d32; "
                        + "margin-bottom: 25px; }");

                out.println("label { display: block; font-weight: bold; "
                        + "margin-bottom: 8px; }");

                out.println("input, textarea, select { width: 100%; "
                        + "padding: 11px; margin-bottom: 18px; "
                        + "border: 1px solid #ccc; border-radius: 5px; "
                        + "font-size: 14px; font-family: Arial; }");

                out.println("textarea { min-height: 120px; resize: vertical; }");

                out.println("input:focus, textarea:focus, select:focus { "
                        + "outline: none; border-color: #2e7d32; }");

                out.println("button { width: 100%; padding: 12px; "
                        + "background: #2e7d32; color: white; border: none; "
                        + "border-radius: 5px; font-size: 15px; "
                        + "cursor: pointer; }");

                out.println("button:hover { background: #1b5e20; }");

                out.println(".retour { display: block; text-align: center; "
                        + "margin-top: 20px; color: #2e7d32; "
                        + "text-decoration: none; }");

                out.println(".retour:hover { text-decoration: underline; }");

                out.println("</style>");
                out.println("</head>");

                out.println("<body>");
                out.println("<div class='container'>");

                out.println("<h2>Modifier une offre</h2>");

                out.println("<form action='" + cp
                        + "/modifierOffre' method='post'>");

                out.println("<input type='hidden' name='id' value='" + id + "'>");

                out.println("<label>Titre :</label>");
                out.println("<input type='text' name='titre' required value='"
                        + echapper(rs.getString("titre")) + "'>");

                out.println("<label>Description :</label>");
                out.println("<textarea name='description' required>"
                        + echapper(rs.getString("description"))
                        + "</textarea>");

                out.println("<label>Entreprise :</label>");
                out.println("<input type='text' name='entreprise' required value='"
                        + echapper(rs.getString("entreprise")) + "'>");

                out.println("<label>Lieu :</label>");
                out.println("<input type='text' name='lieu' value='"
                        + echapper(rs.getString("lieu")) + "'>");

                String contrat = rs.getString("type_contrat");

                out.println("<label>Type de contrat :</label>");
                out.println("<select name='type_contrat'>");

                String[] contrats = {
                    "CDI", "CDD", "Stage", "Alternance", "Freelance"
                };

                for (String c : contrats) {
                    out.println("<option value='" + c + "' "
                            + (c.equals(contrat) ? "selected" : "")
                            + ">" + c + "</option>");
                }

                out.println("</select>");

                java.sql.Date dateLimite = rs.getDate("date_limite");

                out.println("<label>Date limite :</label>");
                out.println("<input type='date' name='date_limite' value='"
                        + (dateLimite == null ? "" : dateLimite.toString())
                        + "'>");

                java.math.BigDecimal salaire = rs.getBigDecimal("salaire");

                out.println("<label>Salaire :</label>");
                out.println("<input type='number' name='salaire' min='0' "
                        + "step='0.01' value='"
                        + (salaire == null ? "" : salaire.toString()) + "'>");

                out.println("<button type='submit'>Enregistrer les modifications</button>");

                out.println("</form>");

                out.println("<a class='retour' href='" + cp
                        + "/listeOffre'>Retour à la liste des offres</a>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (SQLException e) {
            throw new ServletException(
                    "Erreur lors du chargement de l'offre.", e);
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
            afficherErreur(response, "Identifiant invalide.");
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

            afficherErreur(response,
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
            afficherErreur(response, "Date ou salaire invalide.");
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
            ps.setString(4, lieu == null || lieu.isBlank()
                    ? null : lieu.trim());
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
                afficherErreur(response, "Offre introuvable.");
                return;
            }

            response.sendRedirect(
                    request.getContextPath() + "/listeOffre");

        } catch (SQLException e) {
            throw new ServletException(
                    "Erreur lors de la modification de l'offre.", e);
        }
    }
}
