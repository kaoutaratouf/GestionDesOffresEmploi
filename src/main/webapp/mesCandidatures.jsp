
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Candidature" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes candidatures</title>
</head>
<body>

    <h1>Mes candidatures</h1>

    <%
        String message = request.getParameter("message");

        if ("succes".equals(message)) {
    %>
        <p>Votre candidature a été envoyée avec succès.</p>
    <%
        } else if ("deja".equals(message)) {
    %>
        <p>Vous avez déjà postulé à cette offre.</p>
    <%
        }

        List<Candidature> candidatures =
            (List<Candidature>) request.getAttribute("candidatures");
    %>

    <% if (candidatures == null || candidatures.isEmpty()) { %>

        <p>Vous n'avez envoyé aucune candidature.</p>

    <% } else { %>

        <table border="1">
            <tr>
                <th>Offre</th>
                <th>Entreprise</th>
                <th>Date de candidature</th>
                <th>État</th>
            </tr>

            <% for (Candidature c : candidatures) { %>

                <tr>
                    <td><%= c.getTitreOffre() %></td>
                    <td><%= c.getEntreprise() %></td>
                    <td><%= c.getDateCandidature() %></td>
                    <td><%= c.getStatut() %></td>
                </tr>

            <% } %>

        </table>

    <% } %>

    <p><a href="offres">Consulter les offres</a></p>
    <p><a href="accueilCandidat.jsp">Retour à l'accueil</a></p>

</body>
</html>
