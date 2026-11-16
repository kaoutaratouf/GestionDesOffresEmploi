
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Candidature" %>

<%
    if (session.getAttribute("entreprise") == null) {
        response.sendRedirect(request.getContextPath() + "/loginEntreprise.jsp");
        return;
    }

    List<Candidature> candidatures =
        (List<Candidature>) request.getAttribute("candidatures");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Candidatures reçues</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7f5;
            padding: 30px;
            color: #333;
        }

        .container {
            max-width: 1100px;
            margin: 20px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
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
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        tr:hover {
            background: #f1f8f2;
        }

        button {
    border: none;
    padding: 9px 16px;
    border-radius: 5px;
    color: white;
    cursor: pointer;
    font-weight: bold;
    margin: 3px;
}

/* Accepter - Green */
button.accept {
    background-color: #2e7d32;
}

button.accept:hover {
    background-color: #1b5e20;
}

/* Refuser - Red */
button.refuse {
    background-color: #d32f2f;
}

button.refuse:hover {
    background-color: #b71c1c;
}

        .empty {
            text-align: center;
            padding: 25px;
            color: #777;
        }

        .back {
            display: inline-block;
            margin-top: 20px;
            color: #2e7d32;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Les candidatures reçues</h2>

    <table>

        <tr>
            <th>Candidat</th>
            <th>Email</th>
            <th>Offre</th>
            <th>Date de candidature</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>

        <%
            if (candidatures != null && !candidatures.isEmpty()) {

                for (Candidature c : candidatures) {
        %>

        <tr>

            <td>
                <%= c.getNomCandidat() %>
                <%= c.getPrenomCandidat() %>
            </td>

            <td><%= c.getEmailCandidat() %></td>

            <td><%= c.getTitreOffre() %></td>

            <td><%= c.getDateCandidature() %></td>

            <td><%= c.getStatut() %></td>

            <td>

<form action="${pageContext.request.contextPath}/traiterCandidature"
      method="post">

    <input type="hidden" name="idCandidature"
           value="<%= c.getIdCandidature() %>">

    <input type="hidden" name="idOffre"
           value="<%= request.getAttribute("idOffre") %>">

    <input type="hidden" name="statut" value="Acceptée">

    <button type="submit"  class="accept">Accepter</button>
</form>

<form action="${pageContext.request.contextPath}/traiterCandidature"
      method="post">

    <input type="hidden" name="idCandidature"
           value="<%= c.getIdCandidature() %>">

    <input type="hidden" name="idOffre"
           value="<%= request.getAttribute("idOffre") %>">

    <input type="hidden" name="statut" value="Refusée">

    <button type="submit" class="refuse">Refuser</button>
</form>

            </td>

        </tr>

        <%
                }

            } else {
        %>

        <tr>
            <td colspan="6" class="empty">
                Aucune candidature reçue pour le moment.
            </td>
        </tr>

        <%
            }
        %>

    </table>

    <a class="back"
       href="${pageContext.request.contextPath}/listeOffre">
        Retour aux offres
    </a>

</div>

</body>
</html>
