
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Candidature" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Candidatures reçues</title>
</head>
<body>

    <h1>Candidatures reçues</h1>

    <%
        List<Candidature> candidatures =
            (List<Candidature>) request.getAttribute("candidatures");
    %>

    <% if (candidatures == null || candidatures.isEmpty()) { %>

        <p>Aucune candidature reçue.</p>

    <% } else { %>

        <table border="1">
            <tr>
                <th>Candidat</th>
                <th>Email</th>
                <th>Offre</th>
                <th>Date de candidature</th>
                <th>Statut</th>
            </tr>

            <% for (Candidature c : candidatures) { %>
                <tr>
                    <td>
                        <%= c.getPrenomCandidat() %>
                        <%= c.getNomCandidat() %>
                    </td>

                    <td><%= c.getEmailCandidat() %></td>

                    <td><%= c.getTitreOffre() %></td>

                    <td><%= c.getDateCandidature() %></td>

                    <td><%= c.getStatut() %></td>
                </tr>
            <% } %>

        </table>

    <% } %>

    <p><a href="accueilCandidat.jsp">Retour à l'accueil</a></p>

</body>
</html>
