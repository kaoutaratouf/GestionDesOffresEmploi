
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

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f5;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .container {
            background-color: white;
            width: 500px;
            margin: 30px auto;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 25px;
        }

        .top-link {
            text-align: center;
            margin-bottom: 25px;
        }

        .top-link a {
            color: #2e7d32;
            text-decoration: none;
            font-weight: bold;
        }

        .top-link a:hover {
            text-decoration: underline;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
        }

        input[type="text"],
        input[type="date"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 18px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
            font-size: 14px;
        }

        textarea {
            height: 100px;
            resize: vertical;
        }

        input:focus,
        textarea:focus,
        select:focus {
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
        }

        button:hover {
            background-color: #1b5e20;
        }
    </style>
</head>

<body>

    <div class="container">

        <h2>Ajouter une offre d'emploi</h2>

        <div class="top-link">
            <a href="${pageContext.request.contextPath}/listeOffre">
                Voir la liste des offres
            </a>
        </div>

        <form action="${pageContext.request.contextPath}/ajouterOffre" method="post">

            <label>Titre :</label>
            <input type="text" name="titre" required>

            <label>Description :</label>
            <textarea name="description" required></textarea>

            <label>Entreprise :</label>
            <input type="text" name="entreprise" required>

            <label>Lieu :</label>
            <input type="text" name="lieu">

            <label>Type de contrat :</label>
            <select name="type_contrat">
                <option value="CDI">CDI</option>
                <option value="CDD">CDD</option>
                <option value="Stage">Stage</option>
                <option value="Alternance">Alternance</option>
                <option value="Freelance">Freelance</option>
            </select>

            <label>Date limite :</label>
            <input type="date" name="date_limite">

            <label>Salaire :</label>
            <input type="number" name="salaire" step="0.01" min="0">

            <button type="submit">Publier l'offre</button>

        </form>

    </div>

</body>
</html>
