<!DOCTYPE HTML>
<html>

<head>
<title></title>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="content-type"
	content="text/html; charset=windows-1252" />
<link rel="stylesheet" type="text/css"
	href="http://fonts.googleapis.com/css?family=Tangerine&amp;v1" />
<link rel="stylesheet" type="text/css"
	href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz" />
<link rel="stylesheet" type="text/css" href="style/style.css" />
</head>

<body>
	<div id="main">
		<div id="header">
			<div id="logo">
			
				<div class="slogan">Patient Monitoring System</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<!-- put class="current" in the li tag for the selected page - to highlight which page you're on -->

					<li><a class="current" href="hospitalhome.jsp">Home</a></li>
					<li class="hvr-sweep-to-bottom"><a
						href="patientregistration.jsp">Add Patient</a></li>
					<li class="hvr-sweep-to-bottom"><a href="viewpatients.jsp">View
							Patient</a></li>
					<li class="hvr-sweep-to-bottom"><a href="viewpatienthistory.jsp">Search Patient</a></li>
					<li class="hvr-sweep-to-bottom"><a href="logout.jsp">Logout</a></li>
				</ul>
			</div>
		</div>
		<div id="site_content">

			<div id="content">
				<!-- insert the page content here -->
				
				<%
					String status=request.getParameter("status");
							
					if(status!=null)
					{
				%>
						<h1><%=status%></h1>
				<%		
					}
					else
					{
				%>
					 <h1>Patient Registration Form</h1>
				<%		
					}
				%>
				
			<form action="UserRegistration" name="ff" method="post"
				onsubmit="return check()">
				
				<input type="hidden" name="type" value="patient">
				
				<div class="form_settings">
					
					<p>
						<span>User ID</span><input type="text" name="username"/>
					</p>
					
					<p>
						<span>Password</span><input type="password" name="password"/>
					</p>

					<p>
						<span>E-Mail</span><input type="text" name="email" />
					</p>
			
					<p>
						<span>Mobile</span><input type="text" name="mobile">
					</p>

					<p>
						<span>Address</span><input class="contact" type="text" name="address"/>
					</p>
			
					<p style="padding-top: 15px">
						<span>&nbsp;</span><input class="submit" type="submit"
							name="contact_submitted" value="submit"/>
					</p>
					
				</div>
			</form>

		</div>
	</div>
	</div>
	<div id="footer">
		<p>
			Copyright &copy;  | <a href="http://www.voidmaintechnologies.com">info</a>
		</p>
	</div>
</body>
</html>
