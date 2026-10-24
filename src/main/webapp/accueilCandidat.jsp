
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
</head>
<body>

    <h2>Bienvenue <%= candidat.getPrenom() %> !</h2>
    <p>Vous êtes connecté en tant que candidat.</p>

</body>
</html>
