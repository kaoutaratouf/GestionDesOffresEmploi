
package servlet;

import java.io.IOException;

import dao.EntrepriseDAO;
import model.Entreprise;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/modifierEntreprise")
public class ModifierEntrepriseServlet extends HttpServlet {

    // Afficher une page avec le même design
    private void afficherPage(HttpServletResponse response,
                              String titre,
                              String contenu) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html lang='fr'>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<title>" + titre + "</title>" +

            "<style>" +
            "body {" +
                "font-family: Arial, sans-serif;" +
                "background-color: #f4f7f5;" +
                "margin: 0;" +
                "padding: 30px;" +
                "display: flex;" +
                "justify-content: center;" +
                "align-items: center;" +
                "min-height: 90vh;" +
            "}" +

            ".container {" +
                "background-color: white;" +
                "width: 400px;" +
                "padding: 30px;" +
                "border-radius: 10px;" +
                "box-shadow: 0 2px 10px rgba(0,0,0,0.08);" +
            "}" +

            "h2 {" +
                "text-align: center;" +
                "color: #2e7d32;" +
                "margin-bottom: 25px;" +
            "}" +

            "label {" +
                "display: block;" +
                "margin-bottom: 7px;" +
                "font-weight: bold;" +
                "color: #333;" +
            "}" +

            "input[type=text], input[type=email], input[type=password] {" +
                "width: 100%;" +
                "padding: 11px;" +
                "margin-bottom: 18px;" +
                "border: 1px solid #ccc;" +
                "border-radius: 5px;" +
                "box-sizing: border-box;" +
            "}" +

            "input:focus {" +
                "outline: none;" +
                "border-color: #2e7d32;" +
            "}" +

            "button {" +
                "width: 100%;" +
                "padding: 12px;" +
                "background-color: #2e7d32;" +
                "color: white;" +
                "border: none;" +
                "border-radius: 5px;" +
                "cursor: pointer;" +
                "font-size: 15px;" +
            "}" +

            "button:hover {" +
                "background-color: #1b5e20;" +
            "}" +

            ".message {" +
                "text-align: center;" +
                "color: #555;" +
                "margin-bottom: 20px;" +
            "}" +

            ".success {" +
                "color: #2e7d32;" +
            "}" +

            ".error {" +
                "color: #d32f2f;" +
            "}" +

            ".back {" +
                "display: block;" +
                "text-align: center;" +
                "margin-top: 20px;" +
                "color: #2e7d32;" +
                "text-decoration: none;" +
            "}" +

            ".back:hover {" +
                "text-decoration: underline;" +
            "}" +
            "</style>" +

            "</head>" +
            "<body>" +
            "<div class='container'>" +
            contenu +
            "</div>" +
            "</body>" +
            "</html>"
        );
    }

    // Éviter les problèmes avec les caractères HTML
    private String echapper(String valeur) {

        if (valeur == null) {
            return "";
        }

        return valeur.replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;")
                     .replace("'", "&#39;");
    }

    // Afficher le formulaire
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
            int id = Integer.parseInt(request.getParameter("id"));

            EntrepriseDAO dao = new EntrepriseDAO();

            Entreprise entreprise = dao.trouverParId(id);

            if (entreprise == null) {

                afficherPage(
                    response,
                    "Entreprise introuvable",
                    "<h2>Entreprise introuvable</h2>" +
                    "<a class='back' href='" +
                    request.getContextPath() +
                    "/listeEntreprises'>Retour à la liste</a>"
                );

                return;
            }

            String cp = request.getContextPath();

            String contenu =
                "<h2>Modifier une entreprise</h2>" +

                "<form action='" + cp +
                "/modifierEntreprise' method='post'>" +

                "<input type='hidden' name='id' value='" +
                id + "'>" +

                "<label>Nom :</label>" +
                "<input type='text' name='nom' value='" +
                echapper(entreprise.getNom()) +
                "' required>" +

                "<label>Email :</label>" +
                "<input type='email' name='email' value='" +
                echapper(entreprise.getEmail()) +
                "' required>" +

                "<label>Nouveau mot de passe :</label>" +
                "<input type='password' name='mot_de_passe' required>" +

                "<button type='submit'>Enregistrer les modifications</button>" +

                "</form>" +

                "<a class='back' href='" + cp +
                "/listeEntreprises'>Retour à la liste</a>";

            afficherPage(
                response,
                "Modifier une entreprise",
                contenu
            );

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide."
            );

        } catch (Exception e) {

            e.printStackTrace();

            afficherPage(
                response,
                "Erreur",
                "<h2>Erreur</h2>" +
                "<p class='message error'>Erreur lors du chargement de l'entreprise.</p>" +
                "<a class='back' href='" +
                request.getContextPath() +
                "/listeEntreprises'>Retour à la liste</a>"
            );
        }
    }

    // Enregistrer les modifications
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("mot_de_passe");

            Entreprise entreprise = new Entreprise(
                id, nom, email, motDePasse
            );

            EntrepriseDAO dao = new EntrepriseDAO();

            if (dao.modifier(entreprise)) {

                afficherPage(
                    response,
                    "Modification réussie",
                    "<h2>Entreprise modifiée avec succès !</h2>" +
                    "<p class='message success'>Les informations ont été mises à jour.</p>" +
                    "<a class='back' href='" +
                    request.getContextPath() +
                    "/listeEntreprises'>Retour à la liste</a>"
                );

            } else {

                afficherPage(
                    response,
                    "Entreprise introuvable",
                    "<h2>Entreprise introuvable</h2>" +
                    "<p class='message error'>Aucune entreprise n'a été modifiée.</p>" +
                    "<a class='back' href='" +
                    request.getContextPath() +
                    "/listeEntreprises'>Retour à la liste</a>"
                );
            }

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Identifiant invalide."
            );

        } catch (Exception e) {

            e.printStackTrace();

            afficherPage(
                response,
                "Erreur",
                "<h2>Erreur</h2>" +
                "<p class='message error'>Erreur lors de la modification de l'entreprise.</p>" +
                "<a class='back' href='" +
                request.getContextPath() +
                "/listeEntreprises'>Retour à la liste</a>"
            );
        }
    }
}
