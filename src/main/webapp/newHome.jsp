<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Job Application Service</title>
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

    .anyButton:hover {
        background-position: right center;
    }
    </style>
</head>
<body>
    <header>
        
</header>

<script>
        window.onload = function() {
            var dates = document.querySelectorAll('.postdate');
            dates.forEach(function(dateElement) {
                var postdate = dateElement.getAttribute('data-postdate');
                if (postdate) {
                    var formattedDate = postdate.split(' ')[0];
                    dateElement.innerText = formattedDate;
                }
            });
        }
    </script>

<div class="container mt-5">
    <div class="mt-5"></div>
    <div class="profile-header text-center rounded-5 shadow-sm">
        <img src="https://i.imgur.com/uQNIcJO.png" alt="Logo" width="100"/>
    </div>
    
    <div class="row">
        <div class="col-md-12">
        <div class="col-md-12">
        <h3>Job list</h3>
        	<c:if test="${not empty listJob}">
			<c:forEach var="work" items="${listJob}">
			<c:if test="${work.id != 1}">
            <div class="job-listing bg-white p-4 rounded-4 shadow-sm mb-3">
                <h3>${work.title}</h3>
                <p>Date Posted: <span class="postdate" data-postdate="${work.postdate}"></span></p>
                <p>Company: ${work.companyname}</p>
                <p>Location: ${work.address}</p>
                <a href="showJobDetails?id=<c:out value='${work.id}'/>" class="btn btn-1">View Details</a>
            </div>
            </c:if>
            </c:forEach>
            </c:if>
            <c:if test="${empty listJob}">
            <p>No work found.</p>
        	</c:if>
        </div>
        <h3>Users</h3>
        	<c:if test="${not empty listUser}">
				<c:forEach var="user" items="${listUser}">
					<c:if test="${user.id != sessionScope.userId}"> <!-- avoid showing current user in results (NOTE: sessionScope is required to specify we are accessing data from session) -->
						<c:if test="${user.id != 6}">
				            <div class="job-listing bg-white p-4 rounded-4 shadow-sm mb-3">
				            	<img class="profile-pic" src="getImageResult?id=${user.id}" style=" width: 100px; border-radius: 50%;"/>
				                <h3>${user.fname} ${user.lname }</h3>
				                <p>Description: ${user.bio}</p>
				                <p>Location: ${user.district}, ${user.barangay}</p>
				                <a href="user?id=<c:out value='${user.id}'/>" class="btn btn-1">Visit Profile</a>
				            </div>
			            </c:if>
		            </c:if>
	            </c:forEach>
            </c:if>
            <c:if test="${empty listUser}">
            <p>No users found.</p>
        	</c:if>
        </div>
        <div class="col-md-12">
        <h3>Companies</h3>
        	<c:if test="${not empty listCompany}">
				<c:forEach var="company" items="${listCompany}">
					<c:if test="${company.id != sessionScope.companyId}"> <!-- avoid showing the result of the logged in company -->
						<c:if test="${company.id != 1 }"> <!-- The no company row (used for a unique emp id, to specify a user is not employed in a company -->
				            <div class="job-listing bg-white p-4 rounded-4 shadow-sm mb-3">
				            	<img class="profile-pic" src="getImageCompanyResult?id=${company.id}" style=" width: 100px; border-radius: 50%;"/> <!-- 2 diff getImage functions because the sql is different -->
				                <h3>${company.name}</h3>
				                <p>Description: ${company.desc}</p>
				                <p>Location: ${company.address}, ${company.city}, ${company.province}</p>
				                <a href="company?id=<c:out value='${company.id}'/>" class="btn btn-1">Visit Company Profile</a>
				            </div>
			            </c:if>
		            </c:if>
	            </c:forEach>
            </c:if>
            <c:if test="${empty listCompany}">
            <p>No companies found.</p>
        	</c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
