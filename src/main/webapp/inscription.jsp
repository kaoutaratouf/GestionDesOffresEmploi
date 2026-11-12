
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Inscription candidat</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 90vh;
        }

        .container {
            background-color: white;
            width: 380px;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 7px;
            color: #333;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 11px;
            margin-bottom: 17px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input:focus {
            outline: none;
            border-color: #2e7d32;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #2e7d32;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 15px;
            margin-top: 5px;
        }

        button:hover {
            background-color: #1b5e20;
        }

        .success {
            color: #2e7d32;
            text-align: center;
            margin-top: 15px;
        }

        .error {
            color: #d32f2f;
            text-align: center;
            margin-top: 15px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Créer un compte candidat</h2>

    <form action="inscription" method="post">

        <label>Nom :</label>
        <input type="text" name="nom" required>

        <label>Prénom :</label>
        <input type="text" name="prenom" required>

        <label>Email :</label>
        <input type="email" name="email" required>

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required>

        <label>Téléphone :</label>
        <input type="text" name="telephone">

        <button type="submit">S'inscrire</button>

    </form>

    <% if (request.getParameter("succes") != null) { %>
        <p class="success">Inscription réussie !</p>
    <% } %>

    <% if (request.getParameter("erreur") != null) { %>
        <p class="error">
            Erreur lors de l'inscription. Vérifie les données.
        </p>
    <% } %>

</div>

</body>
</html>
