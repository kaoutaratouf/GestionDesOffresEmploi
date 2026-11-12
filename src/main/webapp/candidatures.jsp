
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Candidature" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Candidatures reçues</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .container {
            max-width: 1100px;
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

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            min-width: 700px;
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

        .footer {
            margin-top: 25px;
        }

        .footer a {
            display: inline-block;
            background-color: #2e7d32;
            color: white;
            padding: 10px 18px;
            border-radius: 5px;
            text-decoration: none;
        }

        .footer a:hover {
            background-color: #1b5e20;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Candidatures reçues</h1>

    <%
        List<Candidature> candidatures =
            (List<Candidature>) request.getAttribute("candidatures");
    %>

    <% if (candidatures == null || candidatures.isEmpty()) { %>

        <p class="empty-message">Aucune candidature reçue.</p>

    <% } else { %>

        <div class="table-container">

            <table>
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

        </div>

    <% } %>

    <div class="footer">
        <a href="accueilCandidat.jsp">← Retour à l'accueil</a>
    </div>

</div>

</body>
</html>
