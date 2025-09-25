<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@
include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home - Job Application Service</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <style>
      @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

      body {
        background-image: url("https://i.imgur.com/as1eDDA.png");
        background-size: cover;
        font-family: "Poppins", "sans-serif";
        font-optical-sizing: auto;
        font-weight: auto;
        font-style: normal;
      }
      .profile-header {
        background-color: white;
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
        transition: 0.5s;
      }

      .btn:hover {
        background-position: right center;
      }

      .btn-1 {
        background-image: linear-gradient(
          to right,
          #91cb9c 0%,
          #ffcece 51%,
          #91cb9c 100%
        );
        font-style: bold;
      }

      .navi {
        margin-top: 23px;
        transition: 0.5s;
        background-size: 200% auto;
        transition: 0.5s;
        background-image: linear-gradient(
          to right,
          #91cb9c 0%,
          #ffcece 51%,
          #91cb9c 100%
        );
        font-style: bold;
        border-radius: 7px;
        width: 110px;
        height: auto;
        text-align: center;
        display: inline-block;
        padding: 0.15rem 0.5rem;
      }

      .navi:hover {
        background-position: right center;
      }

      .nav-link {
        margin-top: 23px;
        height: 100%;
        padding: 0.15rem 0.5rem;
      }
      .nav-link img {
        border: 2px solid #80e7b1;
        border-radius: 50%;
      }

      .nav-brand {
        height: 100%;
        padding: 0.15rem 0.5rem;
      }

      .navbar {
        border-bottom: 2px solid #ccc;
        padding-bottom: 10px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        background-size: 97%;
      }

      .anyButton:hover {
        background-position: right center;
      }
    </style>
  </head>
  <body>
    <header></header>

    <div class="container mt-5">
      <div class="mt-5"></div>
      <div class="profile-header text-center rounded-5 shadow-sm">
        <img src="images/peso-1.png" alt="Logo" width="100" />
        <h1 class="fw-bold">Job Listings</h1>
      </div>

      <div class="row">
        <div class="col-md-12">
          <c:forEach var="work" items="${listJob}">
            <c:if test="${work.id != 1}">
              <div class="job-listing bg-white p-4 rounded-4 shadow-sm mb-3">
                <h3>${work.title}</h3>
                <p>Company: ${work.companyname}</p>
                <p>Location: ${work.address}</p>
                <a
                  href="showJobDetails?id=<c:out value='${work.id}' />"
                  class="btn btn-1"
                  >View Applicants</a
                >
                <a
                  href="deleteWork?id=<c:out value='${work.id}' />"
                  class="btn btn-1"
                  >Remove Listing</a
                >
              </div>
            </c:if>
          </c:forEach>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
