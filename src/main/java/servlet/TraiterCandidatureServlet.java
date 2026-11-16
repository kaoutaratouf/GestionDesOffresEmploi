
package servlet;

import java.io.IOException;

import org.hibernate.Session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Entreprise;
import util.HibernateUtil;
import dao.CandidatureDAO;

@WebServlet("/traiterCandidature")
public class TraiterCandidatureServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
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

        // Retrieve the company's email from the session
        String emailEntreprise =
            (String) session.getAttribute("entreprise");

        String idCandidatureParam =
            request.getParameter("idCandidature");

        String statut = request.getParameter("statut");

        if (idCandidatureParam == null ||
            (!"Acceptée".equals(statut) && !"Refusée".equals(statut))) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Paramètres invalides."
            );
            return;
        }

        try {
            int idCandidature =
                Integer.parseInt(idCandidatureParam);

            // Find the company using Hibernate
            String nomEntreprise;

            try (Session hSession =
                     HibernateUtil.getSessionFactory().openSession()) {

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

                nomEntreprise = entreprise.getNom();
            }

            // Update the application status
            CandidatureDAO dao = new CandidatureDAO();

            boolean modifie = dao.changerStatut(
                idCandidature,
                statut,
                nomEntreprise
            );

            if (!modifie) {
                response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Vous ne pouvez pas modifier cette candidature."
                );
                return;
            }

            // Return to the applications page
            String idOffre = request.getParameter("idOffre");

            response.sendRedirect(
                request.getContextPath()
                + "/candidaturesEntreprise?idOffre="
                + idOffre
            );

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide."
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(
                "Erreur lors du traitement de la candidature.", e
            );
        }
    }
}
