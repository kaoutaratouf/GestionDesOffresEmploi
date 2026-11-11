
package servlet;

import java.io.IOException;

import dao.EntrepriseDAO;
import model.Entreprise;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/modifierEntreprise")
public class ModifierEntrepriseServlet extends HttpServlet {

    // Afficher le formulaire
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

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

            Entreprise entreprise = dao.trouverParId(id);

            if (entreprise == null) {
                response.getWriter().println("Entreprise introuvable.");
                return;
            }

            String cp = request.getContextPath();

            response.getWriter().println("<html><body>");

            response.getWriter().println(
                "<h2>Modifier une entreprise</h2>"
            );

            response.getWriter().println(
                "<form action='" + cp
                + "/modifierEntreprise' method='post'>"
            );

            response.getWriter().println(
                "<input type='hidden' name='id' value='" + id + "'>"
            );

            response.getWriter().println("Nom : ");

            response.getWriter().println(
                "<input type='text' name='nom' value='"
                + entreprise.getNom() + "' required><br><br>"
            );

            response.getWriter().println("Email : ");

            response.getWriter().println(
                "<input type='email' name='email' value='"
                + entreprise.getEmail() + "' required><br><br>"
            );

            response.getWriter().println("Ancien mot de passe : ");

            response.getWriter().println(
                "<input type='text' name='ancien_mot_de_passe' value='"
                + entreprise.getMotDePasse() + "' required><br><br>"
            );

            response.getWriter().println("Nouveau mot de passe : ");

            response.getWriter().println(
                "<input type='password' name='mot_de_passe' required><br><br>"
            );

            response.getWriter().println(
                "<input type='submit' value='Enregistrer les modifications'>"
            );

            response.getWriter().println("</form>");

            response.getWriter().println(
                "<br><a href='" + cp
                + "/listeEntreprises'>Retour à la liste</a>"
            );

            response.getWriter().println("</body></html>");

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                "Erreur lors du chargement de l'entreprise."
            );
        }
    }

    // Enregistrer les modifications
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.html"
            );
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("mot_de_passe");

            Entreprise entreprise = new Entreprise(
                id, nom, email, motDePasse
            );

            EntrepriseDAO dao = new EntrepriseDAO();

            if (dao.modifier(entreprise)) {

                response.getWriter().println(
                    "<h2>Entreprise modifiée avec succès !</h2>"
                );

                response.getWriter().println(
                    "<a href='" + request.getContextPath()
                    + "/listeEntreprises'>Retour à la liste</a>"
                );

            } else {

                response.getWriter().println(
                    "Entreprise introuvable."
                );
            }

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                "Erreur lors de la modification de l'entreprise."
            );
        }
    }
}
