<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="navbar.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Edit Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap');

    body {
      background-image: url('https://i.imgur.com/as1eDDA.png');
      background-size: cover;
      font-family: 'Poppins', 'sans-serif';
      font-optical-sizing: auto;
      font-weight: auto;
      font-style: normal;
      margin: 0;
      padding: 0;
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
    background-image: url('images/background.png');
    background-size: 97%;
}

    .container {
      width: 80%;
      max-width: 800px;
      margin: 20px auto;
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      margin-top: 8rem;
    }

    .header {
      background-color: #d5ffdd;
      border-radius: 10px;
      text-align: center;
      margin-bottom: 20px;
      position: relative;
      padding: 1rem;
    }

    .header img {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      border: 2px solid #ddd;
      display: block;
      margin: 0 auto;
    }

    .header .upload-btn {
      margin: 10px auto;
      display: inline-block;
      padding: 5px 5px;
      border: none;
      border-radius: 5px;
      font-size: 14px;
      cursor: pointer;
      background-size: 200% auto;
      transition: 0.5s;
      background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
      font-style: bold;
      color: black;
      font-family: Poppins;
    }

    .header .upload-btn:hover {
      background-position: right center;
    }

    .header .upload-btn input {
      display: none;
    }

    .header h1 {
      margin: 10px 0 5px;
      font-size: 24px;
    }

    .header p {
      font-weight: bold;
      color: #666;
      margin-bottom: 10px;
    }

    .form-group {
      margin-bottom: 15px;
    }

    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }

    .form-group input,
    .form-group textarea {
      width: calc(100% - 20px);
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 16px;
      margin: .5rem 0;
    }

    .form-group textarea {
      resize: vertical;
      height: 100px;
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
    }

    .btn-1 {
      background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
      font-style: bold;
    }

    .btn:hover {
      background-position: right center;
    }

    .btn-1 {
      background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
      font-style: bold;
    }
    
    .work-input {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 1rem;
      width: 90%;
      
    }

    .work-input input {
      width: 100%;
      padding: 5px;
      border-radius: 10px;
      border: 1px solid grey;
      flex-wrap: no-wrap;
    }

    .work-input-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      flex-grow: 1;
      gap: 1rem;
      width: 100%;
    }
    
    .resume label {
    	text-indent: 2.5rem;
    }
    
    .button-container {
    	display: flex;
    	align-items: center;
    	justify-content: center;
    }
    
    .first-container {
    	margin-bottom: 1rem;
    }
    
    #profile-image {
    	object-fit: cover;
    	aspect-ratio: 1 / 1;
    }
    
  </style>
</head>
<header>
</header>
<body>
    
<div class="container">

    <form action="updateProfile" method="post">
    <div class="header">
      <p>Edit Profile</p>
      <img src="getImageResult?id=${sessionScope.userId}" alt="Profile Image" id="profile-image"> <!-- profile image, sessionScope userID means the current user logged in to the session -->
      <!--  <button class="upload-btn">
        Upload Image
        <input type="file" accept="image/*" id="image-upload">
      </button>-->
      <h1>${user.fname} ${user.lname}</h1>
    </div>
        <div class="form-group">
            <label for="fname">First Name:</label>
            <input type="text" id="fname" name="fname" value="${user.fname}" required>
        </div>
        <div class="form-group">
            <label for="lname">Last Name:</label>
            <input type="text" id="lname" name="lname" value="${user.lname}" required>
        </div>
        <div class="form-group">
            <label for="desc">Description:</label>
            <textarea id="desc" name="desc">${user.bio}</textarea>
        </div>
        <div class="form-group">
            <label for="contact">Contact Number:</label>
            <input type="tel" id="contact" name="cnumber" value="${user.cnumber}" required>
        </div>
        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="${user.specificAddress}" required>
            <input type="text" id="district" name="district" value="${user.district}">
            <input type="text" id="barangay" name="barangay" value="${user.barangay}">
        </div>

       
        <div class="resume">
	        <div class="mb-3">
	            <label class="form-label">	Work Experience</label>
	            <c:if test="${not empty user.workHistory}">
		            <div class="work-input-container first-container" id="work-container">
		              <div class="work-input">
		                <input name="job_title_exist[]" class="input-form" placeholder="Job Title" value="${user.workHistory[0].jobTitle}">
		                <input name="company_name_exist[]" class="input-form" placeholder="Company" value="${user.workHistory[0].companyName}">
		                <input name="start_year_exist[]" class="input-form" placeholder="Start Year" value="${user.workHistory[0].startYear}">
		                <input name="end_year_exist[]" class="input-form" placeholder="End Year" value="${user.workHistory[0].endYear}">
		                <button class="btn btn-1 fw-bold userHist" data-id="${user.workHistory[0].workId}" id="addWork">&#43;
		                </button>
		              </div>
		            </div>
	            </c:if>
	              <c:if test="${fn:length(user.workHistory) > 1}">
		            <div class="work-input-container">
		             <c:forEach var="work" items="${user.workHistory}" varStatus="status" begin="1">
		              <div class="work-input" id="work-container-${status.count}">
		                <input name="job_title_exist[]" class="input-form" placeholder="Job Title" value="${work.jobTitle}">
		                <input name="company_name_exist[]" class="input-form" placeholder="Company" value="${work.companyName}">
		                <input name="start_year_exist[]" class="input-form" placeholder="Start Year" value="${work.startYear}">
		                <input name="end_year_exist[]" class="input-form" placeholder="End Year" value="${work.endYear}">
		                <button class="btn btn-1 fw-bold removeWork userHist" data-id="${work.workId}" data-index="${status.count}">&times;
		                </button>
		              </div>
		              </c:forEach>
		            </div>
	            </c:if>
	            <c:if test="${empty user.workHistory}">
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
	            </c:if>
	          </div>
	          
	          
	          <div class="mb-3">
	            <label class="form-label">	Education</label>
	             <c:if test="${not empty user.education}">
		            <div class="work-input-container first-container" id="educ-container">
		              <div class="work-input">
		                <input name="school_name_exist[]" class="input-form" placeholder="School Name" value="${user.education[0].school_name}">
		                <input name="degree_exist[]" class="input-form" placeholder="Degree (Optional)" value="${user.education[0].degree}">
		                <input name="school_start_year_exist[]" class="input-form" placeholder="Start Year" value="${user.education[0].start_year}">
		                <input name="school_end_year_exist[]" class="input-form" placeholder="End Year" value="${user.education[0].end_year}">
		                <button class="btn btn-1 fw-bold userEduc" data-id="${user.education[0].educId}" id="addEduc">&#43;
		                </button>
		              </div>
		            </div>
	            </c:if>
	            
	            <c:if test="${fn:length(user.education) > 1 }">
		            <div class="work-input-container">
		            <c:forEach var="educ" items="${user.education}" varStatus="status" begin="1">
		              <div class="work-input" id="educ-container-${status.count}">
		                <input name="school_name_exist[]" class="input-form" placeholder="School Name" value="${educ.school_name}">
		                <input name="degree_exist[]" class="input-form" placeholder="Degree (Optional)" value="${educ.degree}">
		                <input name="school_start_year_exist[]" class="input-form" placeholder="Start Year" value="${educ.start_year}">
		                <input name="school_end_year_exist[]" class="input-form" placeholder="End Year" value="${educ.end_year}">
		                <button class="btn btn-1 fw-bold removeEduc userEduc" data-id="${educ.educId}" data-index="${status.count}">&times;
		                </button>
		              </div>
		              </c:forEach>
		            </div>
		         </c:if>
	            
	            
	            <c:if test="${empty user.education}">
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
	            </c:if>
	           
	
	          </div>
	           <div class="mb-3">
	            <label class="form-label">	Skills</label>
	 			<c:if test="${not empty user.skills}">
		            <div class="work-input-container" id="skill-container">
		              <div class="work-input">
		                <input name="skill_exist[]" class="input-form" placeholder="Enter skill here" style="width: 100%" value="${user.skills[0].name}">
		                <button class="btn btn-1 fw-bold userSkills" data-id="${user.skills[0].skillId}" id="addSkill">&#43;
		                </button>
		              </div>
		            </div>
		          </div>
	          	</c:if>
	          	
	          	<c:if test="${fn:length(user.skills) > 1}">
		            <div class="work-input-container first-container" >
		            <c:forEach var="skill" items="${user.skills}" varStatus="status" begin="1">
		              <div class="work-input" id="skill-container-${status.count}">
		                <input name="skill_exist[]" class="input-form" placeholder="Enter skill here" style="width: 100%" value="${skill.name}">
		                <button class="btn btn-1 fw-bold removeSkills userSkills" data-id="${skill.skillId}" data-index="${status.count}">&times;
		                </button>
		            </div>
		            </c:forEach>
		          </div>
		        </c:if>
	          	
	          	
	          	<c:if test="${empty user.skills}">
		            <div class="work-input-container first-container" id="skill-container">
		              <div class="work-input">
		                <input name="skill[]" class="input-form" placeholder="Enter skill here" style="width: 100%">
		                <button class="btn btn-1 fw-bold" id="addSkill">&#43;
		                </button>
		            </div>
		          </div>
	          	</c:if>
	          	
	          	<!-- <c:if test="${not empty successMessage}">
			    <p>${successMessage}</p>
				</c:if>
				<c:if test="${not empty errorMessage}">
				    <p>${errorMessage}</p>
				</c:if> -->
				<input type="hidden" id="removedWorkIdsInput" name="removedWorkIds">
				<input type="hidden" id="removedEducIdsInput" name="removedEducIds">
				<input type="hidden" id="removedSkillIdsInput" name="removedSkillIds">
				
				<input type="hidden" id="workIdsInput" name="workIds">
    			<input type="hidden" id="educIdsInput" name="educIds">
    			<input type="hidden" id="skillIdsInput" name="skillIds">
				
				<div class="button-container">
	          	<button type="submit" class="btn btn-1 fw-bold" style="width: 30rem;">Save Changes</button>
	          	</div>
          	</div>
          	</div>
        
		
        
    </form>
    </div>
    
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    

    <!-- Scripts for dynamically adding input fields (for work, education, skills) -->
    <script>
    $(document).ready(function() {
        // Retrieve work IDs from elements with the .removeWork class
        const workIds = $('.userHist[data-id]').map((_, btn) => btn.dataset.id).get();

        // Retrieve education IDs from elements with the .removeEduc class
        const educIds = $('.userEduc[data-id]').map((_, btn) => btn.dataset.id).get();

        // Retrieve skill IDs from elements with the .removeSkills class
        const skillIds = $('.userSkills[data-id]').map((_, btn) => btn.dataset.id).get();

        // Update the hidden input fields with the collected IDs
        $('#workIdsInput').val(workIds.join(','));    // Work IDs
        $('#educIdsInput').val(educIds.join(','));    // Education IDs
        $('#skillIdsInput').val(skillIds.join(','));  // Skill IDs

        // Log the collected IDs for debugging
        console.log('Work IDs:', workIds);
        console.log('Education IDs:', educIds);
        console.log('Skill IDs:', skillIds);
    });
    
    $('form').submit(function(event) {
        // Make sure the hidden input fields are updated with the latest removed IDs
        $('#workIdsInput').val(workIds.join(','));    // Work IDs
        $('#educIdsInput').val(educIds.join(','));    // Education IDs
        $('#skillIdsInput').val(skillIds.join(','));  // Skill IDs
    });
    </script>
    
    
    <script>
    let removedWorkIds = [];
    let removedEducIds = [];
    let removedSkillIds = [];

    // Event listener for removing work
    $(document).on('click', '.removeWork', function(event) {
        event.preventDefault();
        const workId = $(this).data('id');  // Get the skill ID from the data-id attribute
        
        // Push the removed work ID into the array
        removedWorkIds.push(workId);

        console.log(`Removed work ID: ${workId}`);
        
        // Update the hidden input field with the removed IDs
        $('#removedWorkIdsInput').val(removedWorkIds.join(','));
    });

    // Event listener for removing educ
    $(document).on('click', '.removeEduc', function(event) {
        event.preventDefault();
        const educId = $(this).data('id');  // Get the educ ID from the data-id attribute
        
        // Push the removed educ ID into the array
        removedEducIds.push(educId);

        console.log(`Removed educ ID: ${educId}`);
        
        // Update the hidden input field with the removed IDs
        $('#removedEducIdsInput').val(removedEducIds.join(','));
    });

    // Event listener for removing skills
    $(document).on('click', '.removeSkills', function(event) {
        event.preventDefault();
        const skillId = $(this).data('id');  // Get the skill ID from the data-id attribute
        
        // Push the removed skill ID into the array
        removedSkillIds.push(skillId);

        console.log(`Removed skill ID: ${skillId}`);
        
        // Update the hidden input field with the removed IDs
        $('#removedSkillIdsInput').val(removedSkillIds.join(','));

        // Optionally, remove the skill entry from the DOM
        $(this).closest('.work-input').remove();
    });

    // Ensure the hidden fields are updated before form submission
    $('form').submit(function(event) {
        // Make sure the hidden input fields are updated with the latest removed IDs
        $('#removedWorkIdsInput').val(removedWorkIds.join(','));
        $('#removedEducIdsInput').val(removedEducIds.join(','));
        $('#removedSkillIdsInput').val(removedSkillIds.join(','));
    });

    </script>
    
    
  <script>
  $(document).ready(function() {
	    // Work Experience Dynamic Fields
	    let workCount = 0;
	    const workContainer = $('#work-container');
	    
	    $('#addWork').click(function(event) {
	        event.preventDefault(); // Prevent form submission on button click
	        workCount++;

	        // Create new work input entry
	        const entry = $(`
	          <div class="work-input" id="work-entry-${workCount}">
	            <input name="job_title[]" class="input-form" placeholder="Job Title">
	            <input name="company_name[]" class="input-form" placeholder="Company">
	            <input name="start_year[]" class="input-form" placeholder="Start Year">
	            <input name="end_year[]" class="input-form" placeholder="End Year">
	            <button class="removeWork btn btn-1 fw-bold" data-index="${workCount}">&times;</button>
	          </div>
	        `);

	        // Append the new entry to the work container
	        workContainer.append(entry);
	    });

	    // Use event delegation for remove button click in work container
	    workContainer.on('click', '.removeWork', function(event) {
	        event.preventDefault(); // Prevent form submission on button click
	        const index = $(this).data('index'); // Get the index of the clicked remove button
	        const entry = $(`#work-entry-${index}`); // Find the specific entry to remove

	        if (entry.length) {
	            entry.remove(); // Remove the corresponding entry
	        }
	    });

	    // Education Dynamic Fields
	    let educCount = 0;
	    const educContainer = $('#educ-container');
	    
	    $('#addEduc').click(function(event) {
	        event.preventDefault(); // Prevent form submission on button click
	        educCount++;

	        // Create new education input entry
	        const entry = $(`
	          <div class="work-input" id="educ-entry-${educCount}">
	            <input name="school_name[]" class="input-form" placeholder="School Name">
	            <input name="degree[]" class="input-form" placeholder="Degree (Optional)">
	            <input name="school_start_year[]" class="input-form" placeholder="Start Year">
	            <input name="school_end_year[]" class="input-form" placeholder="End Year">
	            <button class="removeEduc btn btn-1 fw-bold" data-index="${educCount}">&times;</button>
	          </div>
	        `);

	        // Append the new entry to the education container
	        educContainer.append(entry);
	    });

	    // Use event delegation for remove button click in education container
	    educContainer.on('click', '.removeEduc', function(event) {
	        event.preventDefault(); // Prevent form submission on button click
	        const index = $(this).data('index'); // Get the index of the clicked remove button
	        const entry = $(`#educ-entry-${index}`); // Find the specific entry to remove

	        if (entry.length) {
	            entry.remove(); // Remove the corresponding entry
	        }
	    });

	    // Skills Dynamic Fields
	    let skillCount = 0;
	    const skillContainer = $('#skill-container');
	    
	    $('#addSkill').click(function(event) {
	        event.preventDefault(); // Prevent form submission on button click
	        skillCount++;

	        // Create new skill input entry
	        const entry = $(`
	          <div class="work-input" id="skill-entry-${skillCount}">
	            <input name="skill[]" class="input-form" placeholder="Enter skill here" style="width: 100%">
	            <button class="removeSkills btn btn-1 fw-bold" data-index="${skillCount}">&times;</button>
	          </div>
	        `);

	        // Append the new entry to the skill container
	        skillContainer.append(entry);
	    });

	    // Use event delegation for remove button click in skill container
	    skillContainer.on('click', '.removeSkills', function(event) {
	        event.preventDefault(); // Prevent form submission on button click
	        const index = $(this).data('index'); // Get the index of the clicked remove button
	        const entry = $(`#skill-entry-${index}`); // Find the specific entry to remove

	        if (entry.length) {
	            entry.remove(); // Remove the corresponding entry
	        }
	    });
	});

</script>

<script>
//Using jQuery for remove buttons
$(document).ready(function() {
    // Event listener for remove buttons in the work container
    $(document).on('click', '.removeWork', function(event) {
        event.preventDefault();
        const index = $(this).data('index'); // Get the index from the button's data-index attribute
        const container = $('#work-container-' + index);

        if (container.length) {
            container.remove();
            console.log(`Work container ${index} removed.`);
        } else {
            console.error(`Work container ${index} not found.`);
        }
    });

    // Event listener for remove buttons in the education container
    $(document).on('click', '.removeEduc', function(event) {
        event.preventDefault();
        const index = $(this).data('index'); // Get the index from the button's data-index attribute
        const container = $('#educ-container-' + index);

        if (container.length) {
            container.remove();
            console.log(`Education container ${index} removed.`);
        } else {
            console.error(`Education container ${index} not found.`);
        }
    });

    // Event listener for remove buttons in the skill container
    $(document).on('click', '.removeSkills', function(event) {
        event.preventDefault();
        const index = $(this).data('index'); // Get the index from the button's data-index attribute
        const container = $('#skill-container-' + index);

        if (container.length) {
            container.remove();
            console.log(`Skill container ${index} removed.`);
        } else {
            console.error(`Skill container ${index} not found.`);
        }
    });
});
</script>
</body>
</html>
