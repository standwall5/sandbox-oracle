<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Company - Job Application Service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>

        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
    
        body {
            background-image: url('https://i.imgur.com/as1eDDA.png');
        background-size: cover;
        font-family:'Poppins', 'sans-serif';
        font-optical-sizing: auto;
        font-weight: auto;
        font-style: normal;
        }
        .profile-header {
            background-color: #d5ffdd;
            padding: 20px;
            text-align: center;
            border-radius: 10px;
            margin-bottom: 20px;
            margin-top: 125px;
        }
        .profile-header img {
            width: 300px;
            border-radius: 50%;
        }
        .post, .description, .employment-history {
            border: 1px solid #e3e3e3;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 15px;
            background-color: #fff;
        }
    
        .btn {
        transition: 0.5s;
        background-size: 200% auto;
        transition: 0.5s;
    }
    
    .btn:hover {
        background-position: right center;
    }
    
    .btn-1 {
        background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
        font-style: bold;
    }
    
    .navi {
        margin-top: 23px;
        transition: 0.5s;
        background-size: 200% auto;
        transition: 0.5s;
        background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
        font-style: bold;
        border-radius: 7px;
        width: 110px;
        height: auto;
        text-align: center;
        display: inline-block;
        padding: 0.15rem .5rem; 
    }
    
    .navi:hover {
        background-position: right center;
    }
    
    .nav-link {
        margin-top: 23px;
        height: 100%; 
        padding: 0.15rem .5rem;
    }
    .nav-link img {
        border: 2px solid #80e7b1;
        border-radius: 50%;
    }
    
    .nav-brand {
        height: 100%; 
        padding: 0.15rem .5rem;
    }
    
    .navbar {
    border-bottom: 2px solid #ccc; 
    padding-bottom: 10px; 
    background-image: url('https://i.imgur.com/as1eDDA.png');
    background-size: 97%;
    }
    
    .anyButton:hover {
        background-position: right center;
    }
    </style>
    </head>
    <body>
    <header>
        <nav class="navbar navbar-expand-md fixed-top mt-2 navbar-dark blur">
            <div class="container-fluid">
            <div>
                <a class="navbar-brand text-dark font-weight-bold" href="index.jsp"><img src="https://i.imgur.com/uQNIcJO.png" width=auto height="75" class="d-inline-block align-text-top"/></a>
            </div>
            <ul class="navList navbar-nav ms-auto">
                <li><a class="navi btn text-dark me-5" href="index.jsp">Home</a></li>
                <li><a href="Logout" class="nav-link text-dark me-5">About us</a></li>
                <li><a href="Logout" class="nav-link text-dark me-5">Service</a></li>
            </ul>
        </div>
        </nav>
    </header>

    <div class="container mt-5">
    <div class="d-flex justify-content-center align-items-center" style="height: 90vh;">
        <div class="p-4 rounded-5 shadow-sm" style="background-color: #d5ffdd;">
            <div class="text-center">
                 <% if (request.getAttribute("errorMessage2") != null) { %>
        	<h1 class="text-danger fw-bold" style="line-height: 1.5;"><%= request.getAttribute("errorMessage2") %></h1>
    		<% } else if (request.getAttribute("successMessage") != null) { %>
    		<h1 class="text-success fw-bold" style="line-height: 1.5;"><%= request.getAttribute("successMessage") %></h1>
    		<% } %>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
