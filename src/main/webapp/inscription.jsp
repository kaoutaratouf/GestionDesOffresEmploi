
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription candidat</title>
</head>
<body>
    <h2>Créer un compte candidat</h2>

    <form action="inscription" method="post">
        <label>Nom :</label>
        <input type="text" name="nom" required>
        <br><br>

        <label>Prénom :</label>
        <input type="text" name="prenom" required>
        <br><br>

        <label>Email :</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required>
        <br><br>

        <label>Téléphone :</label>
        <input type="text" name="telephone">
        <br><br>

        <button type="submit">S'inscrire</button>
    </form>

    <% if (request.getParameter("succes") != null) { %>
        <p>Inscription réussie !</p>
    <% } %>

    <% if (request.getParameter("erreur") != null) { %>
        <p>Erreur lors de l'inscription. Vérifie les données.</p>
    <% } %>
</body>
</html>
