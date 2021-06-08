<%@page import="com.voidmain.pm.dao.AppDAO"%>
<%@page import="com.voidmain.pm.form.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator"%>
<%@page import="java.util.List"%>

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
				
				<%
					String status3=request.getParameter("status");
							
					if(status3!=null)
					{
				%>
						<h1><%=status3%></h1>
				<%		
					}
					else
					{
				%>
					 <h1>Patients</h1>
				<%		
					}
				%>
				
				<table class="imagetable">
					<tr>
						<th>Patient ID</th>
						<th>Address</th>
						<th>Mobile</th>
						<th>Email</th>
						<th>Medicine</th>
						<th>Report</th>
						<th>Surgery</th>
						<th>Discharge</th>
						<th>Status</th>
					</tr>
					<%
								List<User> userForms =AppDAO.getUsersByType("patient");

								Iterator<User> iterator = userForms.iterator();

								while (iterator.hasNext()) {
									
									User userForm = iterator.next();
							%>
							<tr>
								<td><%=userForm.getUserName()%></td>
								<td><%=userForm.getAddress()%></td>
								<td><%=userForm.getMobile()%></td>
								<td><%=userForm.getEmail()%></td>
								<td><a href="adddialymedicine.jsp?patientname=<%=userForm.getUserName()%>">Medicine</a></td>
								<td><a href="addreport.jsp?patientname=<%=userForm.getUserName()%>">Report</a>
								<td><a href="addsurgery.jsp?patientname=<%=userForm.getUserName()%>">Surgery</a></td>
								<td><a href="adddischarge.jsp?patientname=<%=userForm.getUserName()%>">Discharge</a>
								<td><a href="addcurrentstatus.jsp?patientname=<%=userForm.getUserName()%>">Status</a></td>
							</tr>
							<%
								}
							%>		
						</table>
			</div>
		</div>
		<div id="footer">
			<p>
				Copyright &copy;  | <a
					href="http://www.voidmaintechnologies.com">info</a>
			</p>
		</div>
	</div>
</body>
</html>


