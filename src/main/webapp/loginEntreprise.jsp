
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Connexion Entreprise</title>
</head>

<body>

    <h2>Connexion Entreprise</h2>

    <form action="${pageContext.request.contextPath}/loginEntreprise" method="post">

        <label>Email :</label><br>
        <input type="email" name="email" required>

        <br><br>

        <label>Mot de passe :</label><br>
        <input type="password" name="mot_de_passe" required>

        <br><br>

        <button type="submit">Se connecter</button>

    </form>

</body>
</html>
