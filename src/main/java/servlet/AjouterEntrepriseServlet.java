
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

@WebServlet("/ajouterEntreprise")
public class AjouterEntrepriseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");

        Entreprise entreprise = new Entreprise(
            nom, email, motDePasse
        );

        try {
            EntrepriseDAO dao = new EntrepriseDAO();

            if (dao.ajouter(entreprise)) {

                response.getWriter().println(
                    "<h2>Entreprise ajoutée avec succès !</h2>"
                );

                response.getWriter().println(
                    "<a href='" + request.getContextPath()
                    + "/ajouter_entreprise.jsp'>"
                    + "Ajouter une autre entreprise</a>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            response.getWriter().println(
                "<h3>Erreur : impossible d'ajouter l'entreprise.</h3>"
            );
        }
    }
}
