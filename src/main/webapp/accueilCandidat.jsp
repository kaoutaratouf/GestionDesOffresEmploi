
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.Candidat" %>

<%
    Candidat candidat = (Candidat) session.getAttribute("candidat");

    if (candidat == null) {
        response.sendRedirect("connexion.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Accueil candidat</title>

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
            width: 500px;
            padding: 35px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            text-align: center;
        }

        h2 {
            color: #2e7d32;
            margin-bottom: 15px;
        }

        p {
            color: #555;
            margin: 20px 0;
        }

        .menu-link {
            display: block;
            background-color: #2e7d32;
            color: white;
            text-decoration: none;
            padding: 14px;
            margin: 15px 0;
            border-radius: 6px;
            transition: background-color 0.2s;
        }

        .menu-link:hover {
            background-color: #1b5e20;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Bienvenue <%= candidat.getPrenom() %> !</h2>

    <p>Vous êtes connecté en tant que candidat.</p>

    <h2>Espace candidat</h2>

    <a class="menu-link" href="offres">
        Consulter les offres d'emploi
    </a>

    <a class="menu-link" href="mesCandidatures">
        Consulter mes candidatures
    </a>

</div>

</body>
</html>
