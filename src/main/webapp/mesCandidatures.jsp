
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Candidature" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Mes candidatures</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .container {
            max-width: 1000px;
            margin: 30px auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        h1 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 30px;
        }

        .message {
            padding: 12px 15px;
            margin-bottom: 15px;
            border-radius: 5px;
            background-color: #e8f5e9;
            color: #1b5e20;
            border-left: 4px solid #2e7d32;
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            min-width: 650px;
        }

        th {
            background-color: #2e7d32;
            color: white;
            padding: 13px;
            text-align: left;
        }

        td {
            padding: 13px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f8f2;
        }

        .empty-message {
            text-align: center;
            color: #777;
            padding: 20px;
        }

        .links {
            margin-top: 25px;
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .links a {
            display: inline-block;
            background-color: #2e7d32;
            color: white;
            padding: 11px 18px;
            border-radius: 5px;
            text-decoration: none;
        }

        .links a:hover {
            background-color: #1b5e20;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Mes candidatures</h1>

    <%
        String message = request.getParameter("message");

        if ("succes".equals(message)) {
    %>

        <p class="message">
            Votre candidature a été envoyée avec succès.
        </p>

    <%
        } else if ("deja".equals(message)) {
    %>

        <p class="message">
            Vous avez déjà postulé à cette offre.
        </p>

    <%
        }

        List<Candidature> candidatures =
            (List<Candidature>) request.getAttribute("candidatures");
    %>

    <% if (candidatures == null || candidatures.isEmpty()) { %>

        <p class="empty-message">
            Vous n'avez envoyé aucune candidature.
        </p>

    <% } else { %>

        <div class="table-container">

            <table>
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

        </div>

    <% } %>

    <div class="links">
        <a href="offres">Consulter les offres</a>

        <a href="accueilCandidat.jsp">Retour à l'accueil</a>
    </div>

</div>

</body>
</html>
