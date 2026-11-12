
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.math.BigDecimal" %>

<%
    if (session.getAttribute("entreprise") == null) {
        response.sendRedirect(request.getContextPath() + "/loginEntreprise.jsp");
        return;
    }

    ResultSet offres = (ResultSet) request.getAttribute("offres");
%>

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
            margin: 20px auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
        }

        h3 {
            color: #333;
            margin-top: 30px;
            margin-bottom: 15px;
        }

        .navigation {
            margin-bottom: 25px;
        }

        .navigation a {
            display: inline-block;
            background-color: #2e7d32;
            color: white;
            padding: 10px 15px;
            margin-right: 8px;
            margin-bottom: 8px;
            border-radius: 5px;
            text-decoration: none;
        }

        .navigation a:hover {
            background-color: #1b5e20;
        }

        .table-container {
            width: 100%;
            overflow-x: auto;
        }

        table {
            width: 100%;
            min-width: 1100px;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th {
            background-color: #2e7d32;
            color: white;
            padding: 12px;
            text-align: left;
            white-space: nowrap;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            font-size: 14px;
        }

        tr:hover {
            background-color: #f1f8f2;
        }

        td a {
            color: #2e7d32;
            text-decoration: none;
            font-weight: bold;
        }

        td a:hover {
            text-decoration: underline;
        }

        td form {
            display: inline-block;
            margin-left: 8px;
        }

        button {
            background-color: #d32f2f;
            color: white;
            border: none;
            padding: 7px 10px;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #b71c1c;
        }

        .empty {
            text-align: center;
            padding: 20px;
            color: #777;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #2e7d32;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

    <div class="container">

        <h2>Gestion des offres d'emploi</h2>

        <div class="navigation">


            <a href="${pageContext.request.contextPath}/ajouter-offre.jsp">
                Ajouter une offre
            </a>

        </div>

        <h3>Liste des offres d'emploi</h3>

        <div class="table-container">

            <table>

                <tr>
                    <th>ID</th>
                    <th>Titre</th>
                    <th>Description</th>
                    <th>Entreprise</th>
                    <th>Lieu</th>
                    <th>Contrat</th>
                    <th>Date publication</th>
                    <th>Date limite</th>
                    <th>Salaire</th>
                    <th>Statut</th>
                    <th>Actions</th>
                </tr>

                <%
                    boolean trouve = false;

                    if (offres != null) {
                        while (offres.next()) {

                            trouve = true;
                            int id = offres.getInt("id_offre");
                %>

                <tr>

                    <td><%= id %></td>

                    <td><%= offres.getString("titre") %></td>

                    <td><%= offres.getString("description") %></td>

                    <td><%= offres.getString("entreprise") %></td>

                    <td><%= offres.getString("lieu") == null ? "" : offres.getString("lieu") %></td>

                    <td><%= offres.getString("type_contrat") %></td>

                    <td><%= offres.getString("date_publication") %></td>

                    <td><%= offres.getString("date_limite") == null ? "" : offres.getString("date_limite") %></td>

                    <td><%= offres.getBigDecimal("salaire") == null ? "" : offres.getBigDecimal("salaire") %></td>

                    <td><%= offres.getString("statut") %></td>

                    <td>

                        <a href="${pageContext.request.contextPath}/modifierOffre?id=<%= id %>">
                            Modifier
                        </a>

                        <form action="${pageContext.request.contextPath}/supprimerOffre"
                              method="post"
                              onsubmit="return confirm('Supprimer cette offre ?');">

                            <input type="hidden" name="id" value="<%= id %>">

                            <button type="submit">Supprimer</button>

                        </form>

                    </td>

                </tr>

                <%
                        }
                    }

                    if (!trouve) {
                %>

                <tr>
                    <td colspan="11" class="empty">
                        Aucune offre disponible.
                    </td>
                </tr>

                <%
                    }
                %>

            </table>

        </div>

        <a class="back-link" href="${pageContext.request.contextPath}/ajouter-offre.jsp">
            Retour
        </a>

    </div>

</body>
</html>
