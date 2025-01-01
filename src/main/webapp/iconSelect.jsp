<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Sandbox - Homepage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">


<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

    body {
        background-image: url('https://i.imgur.com/as1eDDA.png');
        background-size: cover;
        font-family:'Poppins', 'sans-serif';
        font-optical-sizing: auto;
        font-weight: auto;
        font-style: normal;
        max-height: 1080px;
        overflow: hidden;
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
        height: 100%; 
        padding: 0.15rem .5rem;
    }

    .nav-brand {
        height: 100%; 
        padding: 0.15rem .5rem;
    }

    .navbar {
    border-bottom: 2px solid #ccc; 
    padding-bottom: 10px; 
}

    .anyButton:hover {
        background-position: right center;
    }


.zoomable {
            flex-wrap: wrap;
            zoom: 108%;
        }
        .zoomable label {
            display: inline-block;
            cursor: pointer;
            margin: 10px;
            transition: transform 0.2s ease-in-out;
        }
        .zoomable input[type="radio"] {
            display: none;
        }
        .zoomable img {
            width: 100%;
            max-width: 275px;
            height: auto;
            border: 5px solid transparent;
            border-radius: 50px;
            transition: transform 0.2s ease-in-out, border-color 0.2s ease-in-out;
        }

        .zoomable img:hover{
            transform: scale(1.1);
            border-color: #d8ffba;

        }
        .zoomable input[type="radio"]:checked + img {
            transform: scale(1.1);
            border-color: #d8ffba;
        }



</style>
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-md fixed-top mt-2 navbar-dark blur">
            <div class="container-fluid">
            <div>
                <a class="navbar-brand text-dark font-weight-bold" href="index.html"><img src="https://i.imgur.com/uQNIcJO.png" width=auto height="75" class="d-inline-block align-text-top"/></a>
            </div>
            <ul class="navList navbar-nav ms-auto">
                <li><a class="navi btn text-dark me-5" href="index.html">Home</a></li>
                <li><a href="Logout" class="nav-link text-dark me-5">About us</a></li>
                <li><a href="Logout" class="nav-link text-dark me-5">Service</a></li>
            </ul>
        </div>
        </nav>
    </header>

<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <form action="registerLast" class="text-center" method="post">
    <div class="zoomable d-flex justify-content-center">
        <label>
            <input type="radio" name="icon" value="https://i.imgur.com/oibraT7.jpg">
            <img class="icon" src="https://i.imgur.com/oibraT7.jpg" alt="Icon 1"/>
        </label>
        <label>
            <input type="radio" name="icon" value="https://i.imgur.com/uWOHbRg.jpg">
            <img class="icon" src="https://i.imgur.com/uWOHbRg.jpg" alt="Icon 2"/>
        </label>
        <label>
            <input type="radio" name="icon" value="https://i.imgur.com/Rzckc8T.jpg">
            <img class="icon" src="https://i.imgur.com/Rzckc8T.jpg" alt="Icon 3"/>
        </label>
        <label>
            <input type="radio" name="icon" value="https://i.imgur.com/9Yx1cKV.jpg">
            <img class="icon" src="https://i.imgur.com/9Yx1cKV.jpg" alt="Icon 4"/>
        </label>
    </div>
    <div class="mt-5">
    <button type="submit" class="btn btn-1 fw-bold">Select your icon</button>
    </div>
</form>  
</div>

</body>
</html>