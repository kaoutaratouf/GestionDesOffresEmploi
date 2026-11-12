
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import util.Connexion;

@WebServlet("/ajouterOffre")
public class AjouterOffreServlet extends HttpServlet {

    // Afficher un message avec le même design que les autres pages
    private void afficherMessage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String titre,
                                 String message,
                                 String lien,
                                 String texteLien)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String url = request.getContextPath() + lien;

        response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html lang='fr'>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<title>" + titre + "</title>" +

            "<style>" +
            "body {" +
                "font-family: Arial, sans-serif;" +
                "background-color: #f4f7f5;" +
                "margin: 0;" +
                "padding: 30px;" +
                "display: flex;" +
                "justify-content: center;" +
                "align-items: center;" +
                "min-height: 90vh;" +
            "}" +

            ".container {" +
                "background-color: white;" +
                "width: 400px;" +
                "padding: 35px;" +
                "border-radius: 10px;" +
                "box-shadow: 0 2px 10px rgba(0,0,0,0.08);" +
                "text-align: center;" +
            "}" +

            "h2 {" +
                "color: #2e7d32;" +
                "margin-bottom: 20px;" +
            "}" +

            "p {" +
                "color: #555;" +
                "margin-bottom: 25px;" +
            "}" +

            "a {" +
                "display: inline-block;" +
                "background-color: #2e7d32;" +
                "color: white;" +
                "padding: 12px 20px;" +
                "border-radius: 5px;" +
                "text-decoration: none;" +
            "}" +

            "a:hover {" +
                "background-color: #1b5e20;" +
            "}" +
            "</style>" +

            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<h2>" + titre + "</h2>" +
            "<p>" + message + "</p>" +
            "<a href='" + url + "'>" + texteLien + "</a>" +
            "</div>" +
            "</body>" +
            "</html>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("entreprise") == null) {

            response.sendRedirect(
                request.getContextPath() + "/loginEntreprise.jsp");
            return;
        }

        String emailEntreprise =
            (String) session.getAttribute("entreprise");

        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        String lieu = request.getParameter("lieu");
        String typeContrat = request.getParameter("type_contrat");
        String dateLimite = request.getParameter("date_limite");
        String salaire = request.getParameter("salaire");

        String sqlEntreprise =
            "SELECT nom FROM Entreprise WHERE email = ?";

        String sqlOffre =
            "INSERT INTO offre (titre, description, entreprise, lieu, " +
            "type_contrat, date_limite, salaire) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Connexion.getConnection();
             PreparedStatement psEntreprise =
                 con.prepareStatement(sqlEntreprise)) {

            psEntreprise.setString(1, emailEntreprise);

            try (ResultSet rs = psEntreprise.executeQuery()) {

                if (!rs.next()) {
                    afficherMessage(
                        request, response,
                        "Entreprise introuvable",
                        "L'entreprise associée à ce compte est introuvable.",
                        "/ajouter-offre.jsp",
                        "Retour"
                    );
                    return;
                }

                String nomEntreprise = rs.getString("nom");

                try (PreparedStatement ps =
                         con.prepareStatement(sqlOffre)) {

                    ps.setString(1, titre);
                    ps.setString(2, description);
                    ps.setString(3, nomEntreprise);

                    if (lieu == null || lieu.trim().isEmpty()) {
                        ps.setNull(4, Types.VARCHAR);
                    } else {
                        ps.setString(4, lieu);
                    }

                    if (typeContrat == null ||
                        typeContrat.trim().isEmpty()) {
                        ps.setNull(5, Types.VARCHAR);
                    } else {
                        ps.setString(5, typeContrat);
                    }

                    if (dateLimite == null ||
                        dateLimite.trim().isEmpty()) {
                        ps.setNull(6, Types.DATE);
                    } else {
                        ps.setDate(6, Date.valueOf(dateLimite));
                    }

                    if (salaire == null ||
                        salaire.trim().isEmpty()) {
                        ps.setNull(7, Types.DECIMAL);
                    } else {
                        ps.setBigDecimal(7, new BigDecimal(salaire));
                    }

                    int resultat = ps.executeUpdate();

                    if (resultat > 0) {
                        afficherMessage(
                            request, response,
                            "Offre ajoutée avec succès !",
                            "Votre offre d'emploi a été enregistrée.",
                            "/ajouter-offre.jsp",
                            "Ajouter une autre offre"
                        );
                    }
                }
            }

        } catch (IllegalArgumentException e) {

            afficherMessage(
                request, response,
                "Erreur de saisie",
                "Vérifiez la date limite et le salaire.",
                "/ajouter-offre.jsp",
                "Retour au formulaire"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            afficherMessage(
                request, response,
                "Erreur lors de l'ajout",
                "Une erreur est survenue : " + e.getMessage(),
                "/ajouter-offre.jsp",
                "Retour au formulaire"
            );
        }
    }
}
