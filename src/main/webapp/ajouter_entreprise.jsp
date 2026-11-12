
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter une entreprise</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: white;
            width: 380px;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 18px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 11px;
            background-color: #2e7d32;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 15px;
        }

        input[type="submit"]:hover {
            background-color: #1b5e20;
        }

        .links {
            text-align: center;
            margin-top: 20px;
        }

        a {
            display: inline-block;
            color: #2e7d32;
            text-decoration: none;
            margin: 7px;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

    <div class="container">

        <h2>Ajouter une entreprise</h2>

        <form action="${pageContext.request.contextPath}/ajouterEntreprise" method="post">

            <label>Nom de l'entreprise :</label>
            <input type="text" name="nom" required>

            <label>Email :</label>
            <input type="email" name="email" required>

            <label>Mot de passe :</label>
            <input type="password" name="mot_de_passe" required>

            <input type="submit" value="Ajouter">

        </form>

        <div class="links">

            <a href="${pageContext.request.contextPath}/listeEntreprises">
                Afficher les entreprises
            </a>

            <br>

            <a href="${pageContext.request.contextPath}/login.jsp">
                Retour à la connexion
            </a>

        </div>

    </div>

</body>
</html>
