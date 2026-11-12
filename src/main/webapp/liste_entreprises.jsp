
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Entreprise" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des entreprises</title>

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
            margin: 5px;
            border-radius: 5px;
            text-decoration: none;
        }

        .navigation a:hover {
            background-color: #1b5e20;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th {
            background-color: #2e7d32;
            color: white;
            padding: 13px;
            text-align: left;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f8f2;
        }

        td a {
            text-decoration: none;
            margin-right: 8px;
            color: #2e7d32;
            font-weight: bold;
        }

        td a:hover {
            text-decoration: underline;
        }

        td a:last-child {
            color: #d32f2f;
        }
    </style>
</head>

<body>

    <div class="container">

        <h2>Gestion des entreprises</h2>

        <div class="navigation">

           
            <a href="${pageContext.request.contextPath}/ajouter_entreprise.jsp">
                Ajouter une entreprise
            </a>

            <a href="${pageContext.request.contextPath}/login.jsp">
                Retour à la connexion
            </a>

        </div>

        <h3>Liste des entreprises</h3>

        <table>

            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Email</th>
                <th>Action</th>
            </tr>

            <%
                List<Entreprise> entreprises =
                    (List<Entreprise>) request.getAttribute("entreprises");

                if (entreprises != null) {
                    for (Entreprise entreprise : entreprises) {
            %>

            <tr>

                <td><%= entreprise.getId() %></td>

                <td><%= entreprise.getNom() %></td>

                <td><%= entreprise.getEmail() %></td>

                <td>

                    <a href="${pageContext.request.contextPath}/modifierEntreprise?id=<%= entreprise.getId() %>">
                        Modifier
                    </a>

                    /

                    <a href="${pageContext.request.contextPath}/supprimerEntreprise?id=<%= entreprise.getId() %>"
                       onclick="return confirm('Voulez-vous vraiment supprimer cette entreprise ?');">
                        Supprimer
                    </a>

                </td>

            </tr>

            <%
                    }
                }
            %>

        </table>

    </div>

</body>
</html>
