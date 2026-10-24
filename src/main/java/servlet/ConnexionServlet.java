
package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CandidatDAO;
import model.Candidat;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        CandidatDAO dao = new CandidatDAO();
        Candidat candidat = dao.seConnecter(email, motDePasse);

        if (candidat != null) {
            HttpSession session = request.getSession();
            session.setAttribute("candidat", candidat);

            response.sendRedirect("accueilCandidat.jsp");
        } else {
            response.sendRedirect("connexion.jsp?erreur=1");
        }
    }
}
