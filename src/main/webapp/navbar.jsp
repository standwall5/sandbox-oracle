
 
 <style>
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
    
    .profile-pic {
    width: 100px;
    border-radius: 50%;
    	object-fit: cover;
    	aspect-ratio: 1 / 1;
    }
    
      .nav-pic {
    width: 55px;
    border-radius: 50%;
    	object-fit: cover;
    	aspect-ratio: 1 / 1;
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
    backdrop-filter: blur(20px);
}

 </style>
 
 <nav>
        <nav class="navbar navbar-expand-md fixed-top navbar-dark blur">
        <div class="container-fluid">
        <div>
        <c:if test="${sessionScope.mode == 0}">
            <a class="navbar-brand text-dark font-weight-bold" href="<%=request.getContextPath()%>/newHome"><img src="https://i.imgur.com/uQNIcJO.png" width=auto height="75" class="d-inline-block align-text-top"/></a>
        </c:if>
        <c:if test="${sessionScope.mode == 1}">
            <a class="navbar-brand text-dark font-weight-bold" href="<%=request.getContextPath()%>/newHome"><img src="https://i.imgur.com/uQNIcJO.png" width=auto height="75" class="d-inline-block align-text-top"/></a>
        </c:if>
        </div>
        <form class="d-flex" action="search" method="get">
            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="query">
            <button class="btn btn-1" type="submit">Search</button>
          </form>
        <ul class="navList navbar-nav ms-auto">
        		<!--  <c:if test="${isUser == 1}">
        		<c:if test="${verifyNum == 1}">
        		
					 <li><a class="navi btn text-dark me-5 w-auto" href="companyMode">Company Mode</a></li>
					 <li><a class="navi btn text-dark me-5 w-75" href="userMode">User Mode</a></li>
				</c:if>
				</c:if>-->
            <li><a class="navi btn text-dark me-5" href="<%=request.getContextPath()%>/newHome">Home</a></li>
            <c:if test="${sessionScope.mode == 0}"> <!-- mode = 0 indicates user, 1 is company -->
            <li><a href="<%=request.getContextPath()%>/user?id=<c:out value='${sessionScope.userId}'/>" class="nav-link text-dark me-4" style="padding-right: 20px;"><img class="nav-pic" src="<%=request.getContextPath()%>/getImage" width="50px"  style="position:relative;top:-10px"/></a></li>
            </c:if>
            <c:if test="${sessionScope.mode == 1}">
            <li><a href="<%=request.getContextPath()%>/company?id=<c:out value='${sessionScope.companyId}'/>" class="nav-link text-dark me-4" style="padding-right: 20px;"><img class="nav-pic" src="<%=request.getContextPath()%>/getImageCompany" width="50px"  style="position:relative;top:-10px"/></a></li>
            </c:if>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-dark me-5" style="padding-right: 75px;" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Settings
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <c:if test="${companyID == 6}">
                  <li><a class="dropdown-item" href="regCompanyUser.jsp">Register Company</a></li>
                  </c:if>
                  <li><a class="dropdown-item" href="Logout">Logout</a></li>
                </ul>
              </li>
      
        </ul>
    </div>
    </nav>