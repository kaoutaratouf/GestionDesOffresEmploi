
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Offre" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des offres</title>
</head>
<body>

    <h1>Liste des offres d'emploi</h1>
    
    
<form action="${pageContext.request.contextPath}/rechercherOffre" method="get">
    <label>Rechercher une offre :</label>
    <input type="text" name="motCle" placeholder="Titre ou entreprise">
    <input type="submit" value="Rechercher">
</form>

<br>
    

    <%
        List<Offre> offres =
            (List<Offre>) request.getAttribute("offres");
    %>

    <% if (offres == null || offres.isEmpty()) { %>

        <p>Aucune offre disponible.</p>

    <% } else { %>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Titre</th>
                <th>Description</th>
                <th>Entreprise</th>
                <th>Lieu</th>
                <th>Type de contrat</th>
                <th>Salaire</th>
                <th>Date de publication</th>
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
                </tr>

            <% } %>

        </table>

    <% } %>

    <br>
    <a href="accueilCandidat.jsp">Retour à l'accueil</a>

</body>
</html>
