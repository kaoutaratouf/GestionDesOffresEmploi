
package servlet;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Entreprise;
import model.Offre;
import util.HibernateUtil;

@WebServlet("/listeOffre")
public class ListeOffreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Vérifier la session
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("entreprise") == null) {

            response.sendRedirect(
                request.getContextPath() + "/loginEntreprise.jsp");
            return;
        }

        // Dans ton projet, entreprise contient l'email
        String emailEntreprise =
                (String) session.getAttribute("entreprise");

        try (Session hSession =
                HibernateUtil.getSessionFactory().openSession()) {

            // Récupérer l'entreprise connectée
            Entreprise entreprise = hSession.createQuery(
                    "FROM Entreprise WHERE email = :email",
                    Entreprise.class)
                    .setParameter("email", emailEntreprise)
                    .uniqueResult();

            if (entreprise == null) {

                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Entreprise introuvable."
                );
                return;
            }

            // Récupérer uniquement les offres de cette entreprise
            List<Offre> offres = hSession.createQuery(
                    "FROM Offre WHERE entreprise = :nom ORDER BY idOffre DESC",
                    Offre.class)
                    .setParameter("nom", entreprise.getNom())
                    .list();

            request.setAttribute("offres", offres);

            request.getRequestDispatcher("/liste-offre.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(
                "Erreur lors de la récupération des offres.", e);
        }
    }
}
