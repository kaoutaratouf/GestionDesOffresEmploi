
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Offre" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Liste des offres</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .container {
            max-width: 1200px;
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

        .search-form {
            display: flex;
            gap: 10px;
            margin-bottom: 25px;
            flex-wrap: wrap;
        }

        .search-form input[type="text"] {
            flex: 1;
            min-width: 200px;
            padding: 11px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button,
        input[type="submit"] {
            background-color: #2e7d32;
            color: white;
            border: none;
            padding: 11px 18px;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover,
        input[type="submit"]:hover {
            background-color: #1b5e20;
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            min-width: 900px;
        }

        th {
            background-color: #2e7d32;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f8f2;
        }

        .postuler-form {
            margin: 0;
        }

        .empty-message {
            text-align: center;
            color: #666;
            padding: 20px;
        }

        .footer {
            margin-top: 25px;
        }

        .footer a {
            color: #2e7d32;
            text-decoration: none;
            font-weight: bold;
        }

        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Liste des offres d'emploi</h1>

    <!-- Formulaire de recherche -->
    <form class="search-form"
          action="${pageContext.request.contextPath}/rechercherOffre"
          method="get">

        <input type="text"
               name="motCle"
               placeholder="Titre ou entreprise">

        <input type="submit" value="Rechercher">
    </form>

    <%
        List<Offre> offres =
            (List<Offre>) request.getAttribute("offres");
    %>

    <% if (offres == null || offres.isEmpty()) { %>

        <p class="empty-message">Aucune offre disponible.</p>

    <% } else { %>

        <div class="table-container">

            <table>
                <tr>
                    <th>ID</th>
                    <th>Titre</th>
                    <th>Description</th>
                    <th>Entreprise</th>
                    <th>Lieu</th>
                    <th>Type de contrat</th>
                    <th>Salaire</th>
                    <th>Date de publication</th>
                    <th>Action</th>
                </tr>

                <% for (Offre offre : offres) { %>

                    <tr>
                        <td><%= offre.getIdOffre() %></td>
                        <td><%= offre.getTitre() %></td>
                        <td><%= offre.getDescription() %></td>
                        <td><%= offre.getEntreprise() %></td>
                        <td><%= offre.getLieu() %></td>
                        <td><%= offre.getTypeContrat() %></td>
                        <td><%= offre.getSalaire() %> DH</td>
                        <td><%= offre.getDatePublication() %></td>

                        <td>
                            <!-- Formulaire pour postuler -->
                            <form class="postuler-form"
                                  action="${pageContext.request.contextPath}/postuler"
                                  method="post">

                                <input type="hidden"
                                       name="idOffre"
                                       value="<%= offre.getIdOffre() %>">

                                <button type="submit">
                                    Postuler
                                </button>

                            </form>
                        </td>
                    </tr>

                <% } %>

            </table>

        </div>

    <% } %>

    <div class="footer">
        <a href="accueilCandidat.jsp">
            ← Retour à l'accueil
        </a>
    </div>

</div>

</body>
</html>
