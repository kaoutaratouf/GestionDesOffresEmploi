
package servlet;

import java.io.IOException;

import dao.AdministrateurDAO;
import model.Administrateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");

        try {
            AdministrateurDAO dao = new AdministrateurDAO();

            Administrateur admin = dao.authentifier(email, motDePasse);

            if (admin != null) {

                HttpSession session = request.getSession();
                session.setAttribute("admin", admin.getEmail());

                response.sendRedirect(
                    request.getContextPath() + "/ajouter_entreprise.jsp"
                );

            } else {

                response.getWriter().println(
                    "<h3>Email ou mot de passe incorrect.</h3>"
                );

                response.getWriter().println(
                    "<a href='login.jsp'>Réessayer</a>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            response.getWriter().println(
                "Erreur : " + e.getMessage()
            );
        }
    }
}
