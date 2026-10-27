
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.CandidatureDAO;
import model.Candidature;

@WebServlet("/candidatures")
public class CandidaturesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        CandidatureDAO dao = new CandidatureDAO();

        List<Candidature> candidatures = dao.afficherToutes();

        request.setAttribute("candidatures", candidatures);

        request.getRequestDispatcher("/candidatures.jsp")
               .forward(request, response);
    }
}
