
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.AdministrateurDAO;
import dao.CandidatDAO;

import model.Administrateur;
import model.Candidat;

import util.Connexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");

        try {

            // 1. Vérifier si c'est un administrateur

            AdministrateurDAO adminDAO = new AdministrateurDAO();

            Administrateur admin =
                    adminDAO.authentifier(email, motDePasse);

            if (admin != null) {

                HttpSession session = request.getSession();
                session.setAttribute("admin", admin.getEmail());

                response.sendRedirect(
                    request.getContextPath() + "/ajouter_entreprise.jsp"
                );

                return;
            }

            // 2. Vérifier si c'est une entreprise

            String sql = "SELECT * FROM entreprise "
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
                            request.getContextPath() + "/ajouter-offre.jsp"
                        );

                        return;
                    }
                }
            }

            // 3. Vérifier si c'est un candidat

            CandidatDAO candidatDAO = new CandidatDAO();

            Candidat candidat =
                    candidatDAO.seConnecter(email, motDePasse);

            if (candidat != null) {

                HttpSession session = request.getSession();

                session.setAttribute("candidat", candidat);

                response.sendRedirect(
                    request.getContextPath() + "/accueilCandidat.jsp"
                );

                return;
            }

            // 4. Aucun compte correspondant

            response.sendRedirect(
                request.getContextPath() + "/login.jsp?erreur=1"
            );

        } catch (Exception e) {

            throw new ServletException(
                "Erreur lors de la connexion.", e
            );
        }
    }
}
