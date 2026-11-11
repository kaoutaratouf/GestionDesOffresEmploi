
package servlet;

import java.io.IOException;
import java.util.List;

import dao.EntrepriseDAO;
import model.Entreprise;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/listeEntreprises")
public class ListeEntreprisesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        try {
            EntrepriseDAO dao = new EntrepriseDAO();

            List<Entreprise> entreprises = dao.lister();

            request.setAttribute("entreprises", entreprises);

            request.getRequestDispatcher("/liste_entreprises.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            throw new ServletException(
                "Erreur lors du chargement des entreprises.", e
            );
        }
    }
}
