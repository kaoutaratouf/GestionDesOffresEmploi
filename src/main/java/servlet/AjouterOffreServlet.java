
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.Date;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.Connexion;

import java.sql.ResultSet;

@WebServlet("/ajouterOffre")
public class AjouterOffreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("entreprise") == null) {

            response.sendRedirect(
                request.getContextPath() + "/loginEntreprise.html");
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
            "INSERT INTO offre (titre, description, entreprise, lieu, "
            + "type_contrat, date_limite, salaire) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Connexion.getConnection();
             PreparedStatement psEntreprise =
                 con.prepareStatement(sqlEntreprise)) {

            psEntreprise.setString(1, emailEntreprise);

            try (ResultSet rs = psEntreprise.executeQuery()) {

                if (!rs.next()) {
                    response.getWriter().println(
                        "<h3>Entreprise introuvable.</h3>");
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
                        response.getWriter().println(
                            "<h2>Offre ajoutée avec succès !</h2>");
                        response.getWriter().println(
                            "<a href='" + request.getContextPath()
                            + "/ajouter-offre.jsp'>Ajouter une autre offre</a>");
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            response.getWriter().println(
                "<h3>Erreur : vérifiez la date limite et le salaire.</h3>");
        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("<h3>Erreur lors de l'ajout de l'offre :</h3>");
            response.getWriter().println("<pre>");
            response.getWriter().println(e.getMessage());
            response.getWriter().println("</pre>");
        }
    }
}
