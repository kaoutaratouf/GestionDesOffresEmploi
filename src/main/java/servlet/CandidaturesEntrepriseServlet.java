
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
import model.Candidature;
import util.HibernateUtil;
import dao.CandidatureDAO;

@WebServlet("/candidaturesEntreprise")
public class CandidaturesEntrepriseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("entreprise") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        // The session contains the company's email (String)
        String emailEntreprise =
            (String) session.getAttribute("entreprise");

        String idOffreParam = request.getParameter("idOffre");

        if (idOffreParam == null || idOffreParam.isEmpty()) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "ID de l'offre manquant."
            );
            return;
        }

        try (Session hSession =
                 HibernateUtil.getSessionFactory().openSession()) {

            // Find the company using its email
            Entreprise entreprise = hSession.createQuery(
                "FROM Entreprise WHERE email = :email",
                Entreprise.class
            )
            .setParameter("email", emailEntreprise)
            .uniqueResult();

            if (entreprise == null) {
                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Entreprise introuvable."
                );
                return;
            }

            int idOffre = Integer.parseInt(idOffreParam);

            // Retrieve applications belonging to this company's offer
            CandidatureDAO dao = new CandidatureDAO();

            List<Candidature> candidatures =
                dao.afficherParOffre(idOffre, entreprise.getNom());

            request.setAttribute("candidatures", candidatures);
            request.setAttribute("idOffre", idOffre);

            request.getRequestDispatcher(
                "/candidatures-entreprise.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "ID de l'offre invalide."
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(
                "Erreur lors de l'affichage des candidatures.", e
            );
        }
    }
}
