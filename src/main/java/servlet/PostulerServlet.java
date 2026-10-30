
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CandidatureDAO;
import model.Candidat;

@WebServlet("/postuler")
public class PostulerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("candidat") == null) {
            response.sendRedirect("connexion.jsp");
            return;
        }

        Candidat candidat = (Candidat) session.getAttribute("candidat");

        int idCandidat = candidat.getIdCandidat();
        int idOffre = Integer.parseInt(request.getParameter("idOffre"));

        CandidatureDAO dao = new CandidatureDAO();

        boolean resultat = dao.postuler(idCandidat, idOffre);

        if (resultat) {
            response.sendRedirect("mesCandidatures?message=succes");
        } else {
            response.sendRedirect("mesCandidatures?message=deja");
        }
    }
}
