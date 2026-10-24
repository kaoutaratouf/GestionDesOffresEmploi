
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion candidat</title>
</head>
<body>

    <h2>Connexion candidat</h2>

    <form action="connexion" method="post">

        <label>Email :</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required>
        <br><br>

        <button type="submit">Se connecter</button>

    </form>

    <% if ("1".equals(request.getParameter("erreur"))) { %>
        <p style="color:red;">Email ou mot de passe incorrect.</p>
    <% } %>

</body>
</html>
