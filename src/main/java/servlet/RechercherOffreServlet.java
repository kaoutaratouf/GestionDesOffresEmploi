
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.OffreDAO;
import model.Offre;

@WebServlet("/rechercherOffre")
public class RechercherOffreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String motCle = request.getParameter("motCle");

        OffreDAO dao = new OffreDAO();
        List<Offre> offres = dao.rechercher(
                motCle == null ? "" : motCle.trim());

        request.setAttribute("offres", offres);
        request.setAttribute("motCle", motCle);

        request.getRequestDispatcher("/listeOffres.jsp")
               .forward(request, response);
    }
}
