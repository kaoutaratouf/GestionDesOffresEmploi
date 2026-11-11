
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

@WebServlet("/loginEntreprise")
public class LoginEntrepriseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");

        String sql = "SELECT * FROM Entreprise "
                   + "WHERE email = ? AND mot_de_passe = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, motDePasse);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    HttpSession session = request.getSession();
                    session.setAttribute("entreprise", email);

                    response.sendRedirect(
                    	    request.getContextPath()
                    	    + "/ajouter-offre.jsp");

                } else {

                    response.getWriter().println(
                        "<h3>Email ou mot de passe incorrect.</h3>");

                    response.getWriter().println(
                        "<a href='loginEntreprise.jsp'>Réessayer</a>");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                "<h3>Erreur lors de la connexion.</h3>");
        }
    }
}
