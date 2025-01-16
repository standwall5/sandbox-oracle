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
            flex-direction: column;
            zoom: 200%;
            gap:1rem;
        }
        .zoomable label {
            display: inline-block;
            cursor: pointer;
            margin: 10px;
            transition: transform 0.2s ease-in-out;
        }
        
        .zoomable svg {
        	fill: grey;
            transition: .2s;
            width: 110px;
            border: 3px solid transparent;
            border-radius: 30%;
            transition: transform 0.2s ease-in-out, border-color 0.2s ease-in-out;
        }
        .zoomable svg:hover {
        	fill: black;
            transform: scale(1.1);
            border-color: lightgreen;
        }
            
        

        .zoomable input {
        display: none;
        }
        #fileChange span {
        	color: #3cb474;
        }
        
        #imagePreview {
        	display: none;
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
    <form action="registerLast" class="text-center" method="post" enctype="multipart/form-data">
    <div class="zoomable d-flex justify-content-center">
        
        <label for="icon"><svg xmlns="http://www.w3.org/2000/svg" height="auto" viewBox="0 -960 960 960" width="100px" fill="#e8eaed"><path d="M450-313v-371L330-564l-43-43 193-193 193 193-43 43-120-120v371h-60ZM220-160q-24 0-42-18t-18-42v-143h60v143h520v-143h60v143q0 24-18 42t-42 18H220Z"/></svg></label>
        <input type="file" id="icon" name="icon" accept="image/png, image/jpeg, image/webp"/>
        <p id="fileChange">Select an image</p>
    </div>
    <div>
    <button type="submit" class="btn btn-1 fw-bold" style="zoom: 150%;">Submit</button>
    </div>
</form>  
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<div class="zoomable d-flex justify-content-center">
    <label for="icon">
        <svg xmlns="http://www.w3.org/2000/svg" height="auto" viewBox="0 -960 960 960" width="100px" fill="#e8eaed">
            <path d="M450-313v-371L330-564l-43-43 193-193 193 193-43 43-120-120v371h-60ZM220-160q-24 0-42-18t-18-42v-143h60v143h520v-143h60v143q0 24-18 42t-42 18H220Z"/>
        </svg>
    </label>
    <input type="file" id="icon" name="icon" accept="image/png, image/jpeg, image/webp"/>
    <p id="fileChange">Select an image</p>
    <div id="imagePreviewContainer">
        <img id="imagePreview" src="" alt="Image preview will appear here" style="max-width: 200px; display: none; margin-top: 10px; object-fit: cover; aspect-ratio: 1/1; border-radius: 50%;
        border: 5px solid lightgreen"/>
    </div>
</div>
<div>
    <button type="submit" class="btn btn-1 fw-bold" style="zoom: 150%;">Submit</button>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/intlTelInput.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $('#icon').on('change', function (event) {
            const file = event.target.files[0]; // Get the first file selected

            if (file) {
                // Display the file name
                $('#fileChange').html('Selected file: ' + file.name);

                // Create a FileReader to read the image
                const reader = new FileReader();

                // When the reader loads the file, set it as the src for the image element
                reader.onload = function (e) {
                    // Set the source of the image preview to the file content
                    $('#imagePreview').attr('src', e.target.result);

                    // Show the image preview
                    $('#imagePreview').show();
                };

                // Read the image file as a data URL (base64 encoded string)
                reader.readAsDataURL(file);
            } else {
                $('#fileChange').html('No file selected');
                $('#imagePreview').hide(); // Hide the image preview if no file is selected
            }
        });
    });
</script>

</body>
</html>