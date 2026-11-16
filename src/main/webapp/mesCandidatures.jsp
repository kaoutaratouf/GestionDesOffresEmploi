
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Candidature" %>

<%
    List<Candidature> candidatures =
        (List<Candidature>) request.getAttribute("candidatures");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes candidatures</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7f5;
            padding: 30px;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
        }

        h2 {
            color: #2e7d32;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th {
            background: #2e7d32;
            color: white;
            padding: 12px;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        .attente {
            color: #e69500;
            font-weight: bold;
        }

        .acceptee {
            color: #2e7d32;
            font-weight: bold;
        }

        .refusee {
            color: #d32f2f;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Mes candidatures</h2>

    <table>

        <tr>
            <th>Offre</th>
            <th>Entreprise</th>
            <th>Date de candidature</th>
            <th>Statut de ma candidature</th>
        </tr>

        <%
            if (candidatures != null && !candidatures.isEmpty()) {

                for (Candidature c : candidatures) {

                    String statut = c.getStatut();

                    String classe = "attente";

                    if ("Acceptée".equals(statut)) {
                        classe = "acceptee";
                    } else if ("Refusée".equals(statut)) {
                        classe = "refusee";
                    }
        %>

        <tr>

            <td><%= c.getTitreOffre() %></td>

            <td><%= c.getEntreprise() %></td>

            <td><%= c.getDateCandidature() %></td>

            <td class="<%= classe %>">
                <%= statut == null ? "En attente" : statut %>
            </td>

        </tr>

        <%
                }

            } else {
        %>

        <tr>
            <td colspan="4">
                Vous n'avez pas encore postulé à une offre.
            </td>
        </tr>

        <%
            }
        %>

    </table>

</div>

</body>
</html>
