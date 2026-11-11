
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter une entreprise</title>
</head>
<body>

    <h2>Ajouter une entreprise</h2>

    <form action="${pageContext.request.contextPath}/ajouterEntreprise" method="post">

        <label>Nom de l'entreprise :</label>
        <input type="text" name="nom" required>

        <br><br>

        <label>Email :</label>
        <input type="email" name="email" required>

        <br><br>

        <label>Mot de passe :</label>
        <input type="password" name="mot_de_passe" required>

        <br><br>

        <input type="submit" value="Ajouter">

    </form>

    <br>

    <a href="${pageContext.request.contextPath}/listeEntreprises">
        Afficher les entreprises
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/login.jsp">
        Retour à la connexion
    </a>

</body>
</html>
