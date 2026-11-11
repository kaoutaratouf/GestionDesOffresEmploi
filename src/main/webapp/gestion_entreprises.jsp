
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Entreprise" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des entreprises</title>
</head>
<body>

    <h2>Gestion des entreprises</h2>

    <a href="${pageContext.request.contextPath}/ajouter_entreprise.jsp">
        Ajouter une entreprise
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/listeEntreprises">
        Actualiser la liste
    </a>

    <br><br>

    <table border="1" cellpadding="8">

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

                <a href="${pageContext.request.contextPath}/supprimerEntreprise?id=<%= e.getId() %>"
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
            <td colspan="4">Aucune entreprise trouvée.</td>
        </tr>

        <%
            }
        %>

    </table>

    <br>

    <a href="${pageContext.request.contextPath}/ajouter_entreprise.jsp">
        Ajouter une entreprise
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/login.jsp">
        Retour à la connexion
    </a>

</body>
</html>
