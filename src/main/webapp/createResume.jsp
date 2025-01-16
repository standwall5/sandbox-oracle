<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Resume - Job Application Service</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

    * {
      padding: 0;
      margin: 0;
      box-sizing: border-box;
    }

    body {
      background-image: url('https://i.imgur.com/as1eDDA.png');
      background-size: cover;
      font-family: 'Poppins', 'sans-serif';
      font-optical-sizing: auto;
      font-weight: auto;
      font-style: normal;
      padding: 2rem;
    }

    header {
      margin-bottom: 5rem;
    }

    .profile-header {
      background-color: white;
      padding: 20px;
      text-align: center;
      border-radius: 10px;
      margin-bottom: 20px;
    }

    .profile-header img {
      width: 300px;
      border-radius: 50%;
    }

    .post,
    .description,
    .employment-history {
      border: 1px solid #e3e3e3;
      padding: 15px;
      border-radius: 10px;
      margin-bottom: 15px;
      background-color: #fff;
    }

    .btn {
      transition: 0.5s;
      background-size: 200% auto;
      transition: background-position 0.5s;
      color: white;
      text-shadow: 1px 1px 2px rgba(0, 0, 0, .5);
      width: 6rem;
    }

    .btn:hover {
      background-position: right center;
      color: black;
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
      padding-top: 0;
    }

    .navbar {
      border-bottom: 2px solid #ccc;
      backdrop-filter: blur(20px);
      background-color: rgba(255, 255, 255, 0.3);
    }

    .anyButton:hover {
      background-position: right center;
    }

    label span {
      color: grey;
      font-size: .8rem;
    }

    .input-form {
      background-color: #f8f9fa;
      border: 1px solid #dee2e6;
      border-radius: 5px;
    }

    .profile-header,
    .container-content {
      background-color: #d5ffdd;
    }

    .container-content {
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .work-input {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 1rem;
    }

    .work-input input {
      width: 50%;
      padding: 5px;
      border-radius: 10px;
    }

    .work-input-container {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }

    form {
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
    }
  </style>
</head>

<body>
  <header>
    <nav class="navbar navbar-expand-md fixed-top navbar-dark blur">
      <div class="container-fluid">
        <div>
          <a class="navbar-brand text-dark font-weight-bold" href="index.html"><img src="https://i.imgur.com/uQNIcJO.png" width=auto height="75" class="d-inline-block align-text-top" /></a>
        </div>
        <ul class="navList navbar-nav ms-auto">
          <li><a class="navi btn text-dark me-5" href="index.html">Home</a></li>
          <li><a href="Logout" class="nav-link text-dark me-5">About us</a></li>
          <li><a href="Logout" class="nav-link text-dark me-5">Service</a></li>
        </ul>
      </div>
    </nav>
  </header>

  <!--      -->
  <div class="container">
    <!-- Navigation Bar -->
    <div class="profile-header text-center rounded-5 shadow-sm">

      <h1 class="fw-bold text-black">Create Resume</h1>

    </div>

    <div class="container-content p-4 rounded-5 shadow-sm row mt-4">
      <form action="createResume" method="post">
        <div class="col-md-8">
          <div class="mb-3">
            <label class="form-label">Work Experience</label>
            <div class="work-input-container" id="work-container">
              <div class="work-input">
                <input name="job_title[]" class="input-form" placeholder="Job Title">
                <input name="company_name[]" class="input-form" placeholder="Company">
                <input name="start_year[]" class="input-form" placeholder="Start Year">
                <input name="end_year[]" class="input-form" placeholder="End Year">
                <button class="btn btn-1 fw-bold" id="addWork">&#43;
                </button>
              </div>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">Education</label>
            <div class="work-input-container" id="educ-container">
              <div class="work-input">
                <input name="school_name[]" class="input-form" placeholder="School Name">
                <input name="degree[]" class="input-form" placeholder="Degree (Optional)">
                <input name="school_start_year[]" class="input-form" placeholder="Start Year">
                <input name="school_end_year[]" class="input-form" placeholder="End Year">
                <button class="btn btn-1 fw-bold" id="addEduc">&#43;
                </button>
              </div>
            </div>

          </div>

          <div class="mb-3">
            <label for="companyAddress" class="form-label">Skills</label>
            <div class="work-input-container" id="skill-container">
              <div class="work-input">
                <input name="skill[]" class="input-form" placeholder="Enter skill here" style="width: 100%">
                <button class="btn btn-1 fw-bold" id="addSkill">&#43;
                </button>
              </div>
            </div>
          </div>

          <div class="justify-content-center mx-auto text-center mb-3">
            <% if (request.getAttribute("errorMessage6") != null) { %>
            <small class="text-danger fw-bold"><%= request.getAttribute("errorMessage6") %></small>
            <% } else if (request.getAttribute("successMessage5") != null) { %>
            <small class="text-success fw-bold"><%= request.getAttribute("successMessage5") %></small>
            <% } %>
          </div>

        </div>
        <button type="submit" class="btn btn-1 fw-bold finalbtn">Submit</button>
      </form>
    </div>
  </div>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    let workCount = 0;
    const addWorkbtn = document.getElementById("addWork");
    const workContainer = document.getElementById("work-container");
    addWorkbtn.addEventListener('click', () => {
      event.preventDefault();
      workCount++;
      const entry = document.createElement('div');
      entry.className = 'work-input';
      entry.innerHTML = `
                  <input name="job_title[]" class="input-form" placeholder="Job Title">
                  <input name="company_name[]" class="input-form" placeholder="Company">
                  <input name="start_year[]" class="input-form" placeholder="Start Year">
                  <input name="end_year[]" class="input-form" placeholder="End Year">
                  <button class="btn btn-1 fw-bold" id="removeWork-${workCount}">&times;
                  </button>
                  `;
      workContainer.appendChild(entry);
      const removeWorkbtn = document.getElementById(`removeWork-${workCount}`);
      if (removeWorkbtn) {
        removeWorkbtn.addEventListener('click', () => {
          workContainer.removeChild(entry);
        });
      };
    });
  </script>
  <script>
    let educCount = 0;
    const addEducbtn = document.getElementById("addEduc");
    const educContainer = document.getElementById("educ-container");
    addEducbtn.addEventListener('click', () => {
      event.preventDefault();
      educCount++;
      const entry = document.createElement('div');
      entry.className = 'work-input';
      entry.innerHTML = `
                 <input name="school_name[]" class="input-form" placeholder="School Name">
                  <input name="degree[]" class="input-form" placeholder="Degree (Optional)">
                  <input name="school_start_year[]" class="input-form" placeholder="Start Year">
                  <input name="school_end_year[]" class="input-form" placeholder="End Year">
                  <button class="btn btn-1 fw-bold" id="removeEduc-${educCount}">&times;
                  </button>
                  `;
      educContainer.appendChild(entry);
      const removeEducbtn = document.getElementById(`removeEduc-${educCount}`);
      if (removeEducbtn) {
        removeEducbtn.addEventListener('click', () => {
          educContainer.removeChild(entry);
        });
      };
    });
  </script>
  <script>
    let skillCount = 0;
    const addSkillbtn = document.getElementById("addSkill");
    const skillContainer = document.getElementById("skill-container");
    addSkillbtn.addEventListener('click', () => {
      event.preventDefault();
      skillCount++;
      const entry = document.createElement('div');
      entry.className = 'work-input';
      entry.innerHTML = `
                  <input name="skill[]" class="input-form" placeholder="Enter skill here" style="width: 100%">
              <button class="btn btn-1 fw-bold" id="removeSkill-${skillCount}">&times;
                  `;
      skillContainer.appendChild(entry);
      const removeSkillbtn = document.getElementById(`removeSkill-${skillCount}`);
      if (removeSkillbtn) {
        removeSkillbtn.addEventListener('click', () => {
          skillContainer.removeChild(entry);
        });
      };
    });
  </script>
</body>

</html>