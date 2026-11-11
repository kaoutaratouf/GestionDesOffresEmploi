
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Entreprise" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des entreprises</title>
</head>
<body>

    <h2>Gestion des entreprises</h2>

    <a href="${pageContext.request.contextPath}/listeEntreprises">
        Afficher les entreprises
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/ajouter_entreprise.jsp">
        Ajouter une entreprise
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/login.jsp">
        Retour à la connexion
    </a>

    <br><br>

    <h3>Liste des entreprises</h3>

    <table border="1">
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

</body>
</html>
