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
    .logo {
        width: 500px;
        height: auto;
    }
    .wrapper {
        width: 400px;
        padding: 30px;
        text-align: center;
        left: 25%;
        height: auto;
        margin-left: 250px; 
        margin-right: auto;
        zoom: 125%;
    }
    
    .wrapper2 {
        width: 400px;
        padding: 30px;
        text-align: center;
        right: 25%;
        height: auto;
        margin-left: auto; 
        margin-right: 25px;
        margin-top: -100px;
        zoom: 125%;
    }

    .phone {
        height: 850px;
        width: auto;
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


    @media only screen and (max-width: 768px){

    .body{
        margin: 0 10px;
        max-height: 1024;

    }
    .left-box{
        height: 100px;
        overflow: hidden;
    }
    .right-box{
        padding: 20px;
    }
    .logo {
        width: 250px;
        height: auto;
    }

    .wrapper {
        width: 400px;
        padding: 30px;
        text-align: center;
        left: 25%;
        height: auto;
        margin-left: 250px; 
        margin-right: auto;
        zoom: 125%;
    }

    .wrapper2 {
        width: 400px;
        padding: 30px;
        text-align: center;
        right: 25%;
        height: auto;
        margin-left: 0px; 
        margin-right: 0px;
        margin-top: -25px;
        zoom: 125%;
    }
    .phone {
        height: 325px;
    }
}
</style>

</head>
<body>
    <header>
		<nav class="navbar navbar-expand-md fixed-top mt-5 navbar-dark blur">
            <div class="container-fluid">
			<div>
				<a class="navbar-brand text-dark font-weight-bold"></a>
			</div>
			<ul class="navList navbar-nav ms-auto">
				<li><a class="navi btn text-dark me-5" href="index.jsp">Home</a></li>
				<li><a href="Logout" class="nav-link text-dark me-5">About us</a></li>
                <li><a href="Logout" class="nav-link text-dark me-5">Service</a></li>
			</ul>
        </div>
		</nav>
	</header>
    <div class="container vh-100 position-relative">
        <div class="wrapper position-absolute top-50 start-0 translate-middle-y ms-3 d-flex flex-column align-items-center">
        <img src="https://i.imgur.com/uQNIcJO.png" class="logo"/>
        <br>
        <a href="login.jsp" class="btn btn-1 btn-lg w-50 fs-5 fw-bold">Login</button></a>
        <p class="textbtn fs-6">Start work now!</p>
        <br>
        <a href="register.jsp" class="btn btn-1 btn-lg w-50 fs-5 fw-bold">Register</button></a>
        <p class="textbtn fs-6">Don't have an account? Join now!</p>
        <br>
        <a href="companyindex.jsp" class="btn btn-1 btn-lg w-auto fs-5 fw-bold">For Companies</button></a>
        <p class="textbtn fs-6"">Have a company? Hire now!</p>
        </div>
    </div>
    <div class="container position-relative">
    <div class="wrapper2 position-absolute top-50 end-0 translate-middle-y">
        <img src="https://i.imgur.com/hUXfyfi.png" class="phone"/>
    </div>
</div>

</body>