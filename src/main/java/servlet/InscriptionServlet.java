
package servlet;

import java.io.IOException;

import dao.CandidatDAO;
import model.Candidat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String telephone = request.getParameter("telephone");

        Candidat c = new Candidat(
            nom, prenom, email, motDePasse, telephone
        );

        CandidatDAO dao = new CandidatDAO();

        if (dao.inscrire(c)) {
            response.sendRedirect("inscription.jsp?succes=1");
        } else {
            response.sendRedirect("inscription.jsp?erreur=1");
        }
    }
}
