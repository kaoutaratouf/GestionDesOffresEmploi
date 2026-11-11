
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion Administrateur</title>
</head>
<body>

    <h2>Connexion de l'administrateur</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">

        <label>Email :</label>
        <input type="email" name="email" required>

        <br><br>

        <label>Mot de passe :</label>
        <input type="password" name="mot_de_passe" required>

        <br><br>

        <input type="submit" value="Se connecter">

    </form>

</body>
</html>
