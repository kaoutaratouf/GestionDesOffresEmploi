
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Entreprise" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des entreprises</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: 30px auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
        }

        .actions {
            margin-bottom: 20px;
        }

        a {
            color: #2e7d32;
            text-decoration: none;
            margin-right: 12px;
        }

        a:hover {
            text-decoration: underline;
        }

        .add-link {
            display: inline-block;
            background-color: #2e7d32;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        .add-link:hover {
            background-color: #1b5e20;
            text-decoration: none;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
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

        .delete {
            color: #d32f2f;
        }

        .footer {
            margin-top: 25px;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Gestion des entreprises</h2>

    <div class="actions">
        <a class="add-link"
           href="${pageContext.request.contextPath}/ajouter_entreprise.jsp">
            + Ajouter une entreprise
        </a>

        <a href="${pageContext.request.contextPath}/listeEntreprises">
            Actualiser la liste
        </a>
    </div>

    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>

        <%
            List<Entreprise> entreprises =
                (List<Entreprise>) request.getAttribute("entreprises");

            if (entreprises != null && !entreprises.isEmpty()) {
                for (Entreprise e : entreprises) {
        %>

        <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getEmail() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/modifierEntreprise?id=<%= e.getId() %>">
                    Modifier
                </a>

                |

                <a class="delete"
                   href="${pageContext.request.contextPath}/supprimerEntreprise?id=<%= e.getId() %>"
                   onclick="return confirm('Voulez-vous vraiment supprimer cette entreprise ?');">
                    Supprimer
                </a>
            </td>
        </tr>

        <%
                }
            } else {
        %>

        <tr>
            <td colspan="4" style="text-align:center;">
                Aucune entreprise trouvée.
            </td>
        </tr>

        <%
            }
        %>

    </table>

    <div class="footer">
        <a href="${pageContext.request.contextPath}/ajouter_entreprise.jsp">
            Ajouter une entreprise
        </a>

        |

        <a href="${pageContext.request.contextPath}/login.jsp">
            Retour à la connexion
        </a>
    </div>

</div>

</body>
</html>
