
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if (session.getAttribute("entreprise") == null) {
        response.sendRedirect(request.getContextPath() + "/loginEntreprise.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Ajouter une offre</title>
</head>

<body>

    <h2>Ajouter une offre d'emploi</h2>

    <p>
        <a href="${pageContext.request.contextPath}/listeOffre">
            Voir la liste des offres
        </a>
    </p>

    <form action="${pageContext.request.contextPath}/ajouterOffre" method="post">

        <label>Titre :</label><br>
        <input type="text" name="titre" required>

        <br><br>

        <label>Description :</label><br>
        <textarea name="description" required></textarea>

        <br><br>

        <label>Entreprise :</label><br>
        <input type="text" name="entreprise" required>

        <br><br>

        <label>Lieu :</label><br>
        <input type="text" name="lieu">

        <br><br>

        <label>Type de contrat :</label><br>
        <select name="type_contrat">
            <option value="CDI">CDI</option>
            <option value="CDD">CDD</option>
            <option value="Stage">Stage</option>
            <option value="Alternance">Alternance</option>
            <option value="Freelance">Freelance</option>
        </select>

        <br><br>

        <label>Date limite :</label><br>
        <input type="date" name="date_limite">

        <br><br>

        <label>Salaire :</label><br>
        <input type="number" name="salaire" step="0.01" min="0">

        <br><br>

        <button type="submit">Publier l'offre</button>

    </form>

</body>
</html>
