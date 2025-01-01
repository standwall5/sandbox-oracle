<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Company - Job Application Service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/css/intlTelInput.min.css" rel="stylesheet">
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
    
    .iti{
    	width: 100%;
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

     <div class="container mt-5" style="padding-top:50px; padding-bottom: 50px; ">
    <div class="p-4 rounded-5 shadow-sm row mt-4" style="background-color: #d5ffdd;">
    <div>
        <div class="col-md-8 offset-md-2">
            <form action="RegisterCompany" method="post">
                <div class="mb-3">
                    <label for="companyTitle" class="form-label">Title of Company</label>
                    <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" name="title" required>
                </div>
                <div class="mb-3">
                    <label for="companyTitle" class="form-label">E-mail</label>
                    <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="companyTitle" class="form-label">Password</label>
                    <input type="password" class="form-control form-control-lg bg-light fs-6 rounded-pill" name="pass" required>
                </div>
                <div class="mb-3">
                    <label for="companyTitle" class="form-label">Contact Number</label>
                    <input type="tel" id="phone" class="form-control form-control-lg bg-light fs-6 phone-input rounded-pill"
              name="cnumber" required>
                </div>
                <div class="mb-3">
                    <label for="companyAddress" class="form-label">Address</label>
                    <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" name="address" required>
                </div>
                     <div class="mb-2">
    			<label for="companyAddress" class="form-label">Link to your company picture</label>
                    <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" placeholder="Link (ex. from imgur.com)"
                              name="picture">
    				</div>
                    <div class="input-group mb-3 form-group">
               <textarea class="form-control mt-3" name="desc" rows="4" cols="50" placeholder="Add description to your profile (Optional)"></textarea>
              </div>
             
              <div class="justify-content-center mx-auto text-center mb-3">
            <% if (request.getAttribute("errorMessage3") != null) { %>
        	<small class="text-danger fw-bold"><%= request.getAttribute("errorMessage3") %></small>
    		<% } else if (request.getAttribute("successMessage2") != null) { %>
    		<small class="text-success fw-bold"><%= request.getAttribute("successMessage2") %></small>
    		<% } %>
    		</div>
    		
                 <div class="mb-3">
                    <p class="fw-bold text-danger">Please send the following documents to <span class="text-primary fw-bold">sandboxCompany@gmail.com</span></p>
                    	<ul class="fw-bold fs-6">
				            <li>Certificate of Incorporation</li>
				            <li>Business Registration Certificate</li>
				            <li>Tax Identification Number (TIN)</li>
				            <li>Company's Articles of Association</li>
				            <li>Company's By-Laws</li>
				            <li>Proof of Address (Utility Bill or Lease Agreement)</li>
				            <li>Board Resolution or Minutes of Meeting</li>
				            <li>Director's Identification Documents (Passport, Driver's License, etc.)</li>
				            <li>Annual Return or Financial Statement</li>
				            <li>Business License or Permit</li>
				        </ul>
                </div>
                
                
                <button type="submit" class="btn btn-1 fw-bold">Submit</button>
            </form>
        </div>
    </div>
</div>
</div>

 <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/intlTelInput.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <script>
        document.addEventListener("DOMContentLoaded", function() {
        	const input = document.querySelector("#phone");
        	window.intlTelInput(input, {
        	  initialCountry: "auto",
        	  geoIpLookup: callback => {
        	    fetch("https://ipapi.co/json")
        	      .then(res => res.json())
        	      .then(data => callback(data.country_code))
        	      .catch(() => callback("us"));
        	  },
        	  utilsScript: "/intl-tel-input/js/utils.js?1716383386062"
        	});
        });
    </script>
</body>
</html>
