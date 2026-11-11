
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import util.Connexion;

@WebServlet("/listeOffre")
public class ListeOffreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("entreprise") == null) {

            response.sendRedirect(
                request.getContextPath() + "/loginEntreprise.jsp"
            );
            return;
        }

        String sql = "SELECT * FROM offre ORDER BY id_offre DESC";

        try {
            Connection con = Connexion.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            request.setAttribute("offres", rs);

            request.getRequestDispatcher("/liste-offre.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(
                "Erreur lors de la lecture des offres.", e
            );
        }
    }
}
