
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CandidatureDAO;
import model.Candidat;
import model.Candidature;

@WebServlet("/mesCandidatures")
public class MesCandidaturesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("candidat") == null) {
            response.sendRedirect("connexion.jsp");
            return;
        }

        Candidat candidat = (Candidat) session.getAttribute("candidat");

        CandidatureDAO dao = new CandidatureDAO();

        List<Candidature> candidatures =
                dao.afficherParCandidat(candidat.getIdCandidat());

        request.setAttribute("candidatures", candidatures);

        request.getRequestDispatcher("/mesCandidatures.jsp")
               .forward(request, response);
    }
}
