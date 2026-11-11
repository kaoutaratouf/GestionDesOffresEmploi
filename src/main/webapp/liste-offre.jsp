
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
</head>

<body>

    <h2>Gestion des offres d'emploi</h2>

    <p>
        <a href="${pageContext.request.contextPath}/listeOffre">
            Afficher la liste des offres
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/ajouter-offre.jsp">
            Ajouter une offre
        </a>
    </p>

    <h3>Liste des offres d'emploi</h3>

    <table border="1" cellpadding="7">

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
            <td colspan="11">Aucune offre disponible.</td>
        </tr>

        <%
            }
        %>

    </table>

    <br>

    <a href="${pageContext.request.contextPath}/ajouter-offre.jsp">
        Retour
    </a>

</body>
</html>
