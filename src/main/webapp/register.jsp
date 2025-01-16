<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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

.box-area{
    width: 1100px;
    background: #d5ffdd;
	border: 2px solid rgba(255,255,255, .2);
	backdrop-filter: blur(10px);
}

.right-box{
    padding: 40px 30px 40px 40px;
}

::placeholder{
    font-size: 16px;
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

.anyButton {
        transition: 0.5s;
        background-size: 200% auto;
        transition: 0.5s;
        background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
        font-style: bold;
        width: 75px;
        border-radius: 7px;
        padding: 0.15rem .5rem; 
    }

    .anyButton:hover {
        background-position: right center;
    }

@media only screen and (max-width: 768px){

    .box-area{
        margin: 0 10px;

    }
    .left-box{
        height: 100px;
        overflow: hidden;
    }
    .right-box{
        padding: 20px;
    }

}
    </style>
  </head>
  <body style="background-image: url('https://i.imgur.com/as1eDDA.png'); width: 100%; height: 100vh; background-size: cover;">
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
    <!-----------Main Container----------->
    <div class="container d-flex justify-content-center align-items-center min-vh-100">

    <!-----------Login Container----------->
      <div class="row border rounded-5 p-3 shadow box-area">

    <!-----------Left side----------->
        <div class="left-box col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column">
          <div class="featured-image">
            <img src="https://i.imgur.com/ckHYvsR.png" class="img-fluid" style="width: 400px"/>
          </div>
        </div>
    <!-----------Right side----------->
        <div class="right-box col-md-6">
          <div class="row align-items-center">
            <div class="header-text mb-3">
              <h3 class="fw-bold">Sign-up</h3>
              <p>Welcome</p>
            </div>
            <form action="RegisterUser" method="post">
            <div class="input-group mb-3 form-group">
              <input type="text" class="form-control form-control-lg bg-light me-2" placeholder="First Name"
						name="fname" required="required">
			  <input type="text" class="form-control form-control-lg bg-light" placeholder="Last Name"
						name="lname" required="required">
            </div>
            <div class="input-group mb-3 form-group">
              <input type="email" class="form-control form-control-lg bg-light fs-6" placeholder="E-Mail Address"
						name="email" required="required">
            </div>
            <div class="input-group mb-3 form-group">
              <input type="password" class="form-control form-control-lg bg-light fs-6" placeholder="Password"
              name="password" required="required">
            </div>
             <div class="input-group mb-1 form-group">
              <input type="password" class="form-control form-control-lg bg-light fs-6" placeholder="Confirm Password"
              name="password_repeat" required="required">
            </div>
            <div class="input-group mb-4 d-flex justify-content-between">
              <div class="form-check">
                <input type="checkbox" class="form-check-input" id="formCheck">
                <label for="formCheck" class="form-check-label text-black"><small>Remember Me</small></label>
              </div>
              <div class="forgot">
                  <small><a href="#" style="text-decoration: none; color: black;">Forgot Password?</a></small>
              </div>
              </div>
               <div class="justify-content-center mx-auto text-center">
    		</div>
    		    <div class="justify-content-center mx-auto text-center">
            <% if (request.getAttribute("errorMessage2") != null) { %>
        	<small class="text-danger"><%= request.getAttribute("errorMessage2") %></small>
    		<% } else if (request.getAttribute("successMessage") != null) { %>
    		<small class="text-success"><%= request.getAttribute("successMessage") %></small>
    		<% } %>
    		</div>
            <div class="input-group mb-3">
              <button class="btn btn-1 btn-lg w-100 fs-6 fw-bold" type="submit">Register</button>
            </div>
            </form>
            <div class="row">
              <small class="text-wrap text-center ms-3 fw-bold"><a href="login.jsp" style="text-decoration: none; color: black;">Go to Login</a></small>
            </div>

          </div>
              
        </div>  

      </div>
    </div>
<!-- End of right and all boxes -->

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
    crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
    integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" 
    crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" 
    integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" 
    crossorigin="anonymous"></script>
  </body>
</html>