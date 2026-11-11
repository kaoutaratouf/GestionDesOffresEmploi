
package servlet;

import java.io.IOException;

import dao.EntrepriseDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/supprimerEntreprise")
public class SupprimerEntrepriseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.html"
            );
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            EntrepriseDAO dao = new EntrepriseDAO();

            boolean resultat = dao.supprimer(id);

            if (resultat) {

                response.sendRedirect(
                    request.getContextPath() + "/listeEntreprises"
                );

            } else {

                response.getWriter().println(
                    "Aucune entreprise trouvée avec cet identifiant."
                );
            }

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide."
            );

        } catch (Exception e) {

            throw new ServletException(
                "Erreur lors de la suppression de l'entreprise.", e
            );
        }
    }
}
