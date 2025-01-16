<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Sandbox - Homepage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/css/intlTelInput.min.css" rel="stylesheet">

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap');

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

    .box-area{
    width: 2000px;
    background: #d5ffdd;
	border: 2px solid rgba(255,255,255, .2);
	backdrop-filter: blur(10px);
    zoom: 110%;
    border-radius:18px;
}

.iti { width: 100%; }

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

    <div class="row border rounded-5 p-3 shadow box-area">
    <div class="left-box col-md-6 rounded-5">
        <form action="registerInfo" method="post">
            <div class="input-group mb-3 form-group mt-4">
              <input type="number" class="form-control form-control-lg bg-light fs-6 rounded-pill" placeholder="Age"
					name="age" required>
            </div>
            <div class="input-group mb-3 form-group">
              <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" placeholder="Birth Date"
					name="bday" required onfocus="(this.type='date')" onblur="(this.type='text')">
            </div>
            
            <div class="input-group mb-3 form-group">
              <input type="text" id="phone" class="form-control form-control-lg bg-light fs-6 phone-input rounded-pill"
              name="cnumber" placeholder="Contact Number" required>
            </div>
            <div class="input-group mb-3 form-group">
              <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" placeholder="House Number, Street, Subdivision, Etc."
              name="specific_address" required>
            </div>
              <div class="input-group mb-3 form-group">
               <select class="form-select form-select-lg bg-light fs-6 rounded-pill" id="categorySelect" name="district" aria-label="Default select example" required>
                    <option hidden disabled>Select district</option>
                    <option value="District I">District I</option>
                    <option value="District II">District II</option>
                  </select>
                 </div>
                 <div class="input-group mb-3 form-group">
                        <select class="form-select form-select-lg bg-light fs-6 rounded-pill" id="subcategorySelect" name="barangay" required>
                        	<option selected hidden disabled>Select Barangay</option>
                        </select>
                    </div>
                   <div class="input-group mb-2 form-group">
               <textarea class="form-control mt-3" name="bio" rows="4" cols="50" placeholder="Add description to your profile (Optional)"></textarea>
    </div>
    </div>
    <div class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column right-box">
        <div class="featured-image">
          <img src="https://i.imgur.com/ckHYvsR.png" class="img-fluid" style="width: 325px"/>
        </div>
        <div class="justify-content-center mx-auto text-center mb-3">
            <% if (request.getAttribute("errorMessage2") != null) { %>
        	<small class="text-danger"><%= request.getAttribute("errorMessage2") %></small>
    		<% } else if (request.getAttribute("successMessage") != null) { %>
    		<small class="text-success"><%= request.getAttribute("successMessage") %></small>
    		<% } %>
    		</div>

        <div class="mb-5">
            <button type="submit" class="btn btn-1 fw-bold">Next</button>
            </div>
        </div>
        </form>
    </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

   <script>
    $(document).ready(function() {
        var categoryOptions = {
        		"District I": [
        	        { value: "Baclaran", desc: "Baclaran" },
        	        { value: "Tambo", desc: "Tambo" },
        	        { value: "Dongalo", desc: "Dongalo" },
        	        { value: "Sto. Niño", desc: "Sto. Niño" },
        	        { value: "La Huerta", desc: "La Huerta" },
        	        { value: "San Dionisio", desc: "San Dionisio" },
        	        { value: "San Isidro", desc: "San Isidro" },
        	        { value: "Vitalez", desc: "Vitalez" }
        	    ],
        	    "District II": [
        	        { value: "San Antonio", desc: "San Antonio" },
        	        { value: "B.F. Homes", desc: "B.F. Homes" },
        	        { value: "Sun Valley", desc: "Sun Valley" },
        	        { value: "Marcelo Green", desc: "Marcelo Green" },
        	        { value: "Don Bosco", desc: "Don Bosco" },
        	        { value: "Merville", desc: "Merville" },
        	        { value: "San Martin de Porres", desc: "San Martin de Porres" },
        	        { value: "Moonwalk", desc: "Moonwalk" }
        	        ]
        };

        function updateSubcategoryOptions(categoryId) {
            var subcategorySelect = $('#subcategorySelect');
            subcategorySelect.empty();

            var options = categoryOptions[categoryId];

            options.forEach(function(option) {
                subcategorySelect.append($('<option>').text(option.desc).val(option.value));
            });
        }

        var initialCategoryId = $('#categorySelect').val();
        updateSubcategoryOptions(initialCategoryId);

        $('#categorySelect').on('change', function() {
            var selectedCategoryId = $(this).val();
            updateSubcategoryOptions(selectedCategoryId);
        });
    });
</script>
</body>
</html>
