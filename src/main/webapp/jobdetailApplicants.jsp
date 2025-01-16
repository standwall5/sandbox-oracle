<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Details - Job Application Service</title>
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
    .applicant-info {
    	display: flex;
    	justify-content: space-around;
    	align-items: center;
    	padding: 1rem;
    }
    .demographic {
    	line-height: 2.2rem;
    }
    
    </style>
</head>
<body>
    <header>
</header>

<script>
        window.onload = function() {
            var postdate = "${work.postdate}";
            var formattedDate = postdate.split(' ')[0];
            document.getElementById('postdate').innerText = formattedDate;
        }
    </script>

<div class="container mt-5">
    <div class="profile-header text-center rounded-5 shadow-sm">
        <img src="https://i.imgur.com/uQNIcJO.png" alt="Logo" width="100"/>
		<h1 class="fw-bold">Job Details</h1>
    </div>
    
    <div class="row mt-5">
        <div class="col-md-12">
            <div class="job-details bg-white p-4 rounded shadow-sm rounded-4">
                <h3>${work.title}</h3>
                <p><strong>Date Posted:</strong> <span id="postdate"></span></p>
                <p><strong>Company:</strong> ${work.companyname}</p>
                <p><strong>Category:</strong> ${work.category}</p>
                <p><strong>Location:</strong> ${work.address}</p>
                <p><strong>Description:</strong> ${work.desc}</p>
                
                <c:choose>
                    <c:when test="${mode == 0}">
                    	<c:if test="${exists == 0 }"> <!-- if they havent applied yet -->
                        	<button id="applyButton" class="btn btn-1 fw-bold" data-work-id="${work.id}">Apply now with resume created</button>
                        </c:if>
                        <c:if test="${exists == 1 }">
                        	<button id="withdrawButton" class="btn btn-1 fw-bold" data-work-id="${work.id}">Withdraw application</button>
                        </c:if>
                    </c:when>
                    <c:when test="${mode == 1}">
                        <button class="btn btn-1 fw-bold" onclick="window.location.href = 'joblistCompany';">View all job listings</button>
                    </c:when>
                </c:choose>
            </div>
            
            <c:if test="${sessionScope.mode == 1}">
    <div class="mt-4">
        <h3>List of Applicants:</h3>
        <c:if test="${not empty listUser}">
            <c:forEach var="user" items="${listUser}">
                <div class="job-listing bg-white p-4 rounded-4 shadow-sm mb-3">
                	<div class="applicant-info">
                	<div class="demographic">
                    <h4>${user.fname} ${user.lname}</h4>
                    <p><strong>Email:</strong> ${user.email}</p>
                    <p><strong>Contact Number:</strong> ${user.cnumber}</p>
                    <p><strong>District:</strong> ${user.district}</p>
                    <p><strong>Barangay:</strong> ${user.barangay}</p>
                    <p><strong>Address:</strong> ${user.specificAddress}</p>
                    <p><strong>Description:</strong> ${user.bio}</p>
                    </div>
                    <div class="resume">
                    <h4>Work, Education & Skills</h4>
                    <c:if test="${not empty user.workHistory}">
                    	<p><strong>Work History:</strong><br> 
                    	<c:forEach var="workHist" items="${user.workHistory}">
                    		${workHist.jobTitle} | ${workHist.companyName} | ${workHist.startYear} - ${workHist.endYear}</p>
                    	</c:forEach>
                    </c:if>
                     <c:if test="${not empty user.education}">
                     	<p><strong>Educational History:</strong> <br>
                    	<c:forEach var="educ" items="${user.education}">
                    		${educ.school_name} | ${educ.degree} | ${educ.start_year} | ${educ.end_year}</p>
                    	</c:forEach>
                    </c:if>
                      <c:if test="${not empty user.skills}">
                      	<p><strong>Skills:</strong> <br>
                    	<c:forEach var="skills" items="${user.skills}">
                    		${skills.name}</p>
                     	</c:forEach>
                    </c:if>
                    </div>
                    </div>
                    
                    
                   
                       <button class="btn btn-1 fw-bold" onclick="confirmAccept('<c:out value="${user.id}" />', '<c:out value="${work.id}" />')">Accept</button>
<button class="btn btn-1 fw-bold" onclick="confirmReject('<c:out value="${user.id}" />', '<c:out value="${work.id}" />')">Reject</button>

                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty listUser}">
            <p>No applicants found.</p>
        </c:if>
    </div>
</c:if>

        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
$(document).ready(function() {

    $('#applyButton').click(function() {

        var workId = $(this).data('work-id');

        $.get('apply?id=' + workId, function(response) {

            if (response === 'success') {
                console.log('Application submitted successfully');

                Swal.fire({
                    icon: 'success',
                    title: 'Application Submitted Successfully!',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    // Refresh the page after the success notification
                    location.reload();
                });
            } else if (response === 'already_applied') {
                console.log('User has already applied to this job');

                Swal.fire({
                    icon: 'info',
                    title: 'Already Applied',
                    text: 'You have already applied to this job.',
                });
            }
        }).fail(function(xhr, status, error) {

            console.error('Error submitting application:', error);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Failed to submit application. Please try again.',
            });
        });
    });
    
    $('#withdrawButton').click(function() {

        var workId = $(this).data('work-id');

        $.get('withdrawApp?id=' + workId, function(response) {

            if (response === 'success') {
                console.log('Application submitted successfully');

                Swal.fire({
                    icon: 'success',
                    title: 'Application Withdrawn.',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    // Refresh the page after the success notification
                    location.reload();
                });
            } else {
                console.log('Withraw unsuccessful');

                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Failed to withdraw application. Please try again.',
                });
            }
        }).fail(function(xhr, status, error) {

            console.error('Error submitting application:', error);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Failed to withdraw application. Please try again.',
            });
        });
    });
});
</script>

<script>
    function confirmAccept(userId, workId) {
        Swal.fire({
            title: 'Are you sure?',
            text: 'You are about to accept this applicant.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, accept!'
        }).then((result) => {
            if (result.isConfirmed) {

                $.get('acceptApplicant', { userId: userId, workId: workId })
                    .done(function(response) {
                        if (response === 'success') {
                            Swal.fire({
                                icon: 'success',
                                title: 'Accepted!',
                                text: 'The applicant has been accepted.',
                            }).then(() => {

                                location.reload();
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error!',
                                text: 'Failed to accept the applicant. Please try again.',
                            });
                        }
                    })
                    .fail(function(xhr, status, error) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error!',
                            text: 'Failed to accept the applicant. Please try again.',
                        });
                    });
            }
        });
    }

    function confirmReject(userId, workId) {
        Swal.fire({
            title: 'Are you sure?',
            text: 'You are about to reject this applicant.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, reject!'
        }).then((result) => {
            if (result.isConfirmed) {

                $.get('rejectApplicant', { userId: userId, workId: workId })
                    .done(function(response) {
                        if (response === 'success') {
                            Swal.fire({
                                icon: 'success',
                                title: 'Rejected!',
                                text: 'The applicant has been rejected.',
                            }).then(() => {

                                location.reload();
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error!',
                                text: 'Failed to reject the applicant. Please try again.',
                            });
                        }
                    })
                    .fail(function(xhr, status, error) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error!',
                            text: 'Failed to reject the applicant. Please try again.',
                        });
                    });
            }
        });
    }
</script>
</body>
</html>
