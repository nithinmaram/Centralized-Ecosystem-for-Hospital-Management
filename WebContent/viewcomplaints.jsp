<!DOCTYPE HTML>
<%@page import="com.voidmain.pm.dao.AppDAO"%>
<%@page import="com.voidmain.pm.form.Report"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
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
			
				<div class="slogan">Patient Monitoring System<</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<!-- put class="current" in the li tag for the selected page - to highlight which page you're on -->

					<li><a class="current" href="adminhome.jsp">Home</a></li>
					<li class="hvr-sweep-to-bottom"><a href="viewhospitals.jsp">Hospitals</a></li>
					<li class="hvr-sweep-to-bottom"><a href="viewadminpatients.jsp">Patients</a></li>
					<li class="hvr-sweep-to-bottom"><a href="viewcomplaints.jsp">Reports</a></li>
					<li class="hvr-sweep-to-bottom"><a href="logout.jsp">Logout</a></li>
					
				</ul>
			</div>
		</div>
		<div id="site_content">
			<div id="content">

				<%
					String status3 = request.getParameter("status");

					if (status3 != null) {
				%>
				<h1><%=status3%></h1>
				<%
					} else {
				%>
				<h1>View Reportings</h1>
				<%
					}
				%>

				<table class="imagetable">
					<tr>
						<th>ID</th>
						<th>Patient</th>
						<th>Reported By</th>
					</tr>
					<%
						List<Report> reports = AppDAO.getReportings();

						Iterator<Report> iterator = reports.iterator();

						while (iterator.hasNext()) {

							Report report = iterator.next();
					%>
					<tr>
						<td><%=report.getId()%></td>
						<td><%=report.getPatientName()%></td>
						<td><%=report.getReportedBy()%></td>
					<%
						}
					%>
				</table>
			</div>
		</div>
		<div id="footer">
			<p>
				Copyright &copy; 2018 | <a
					href="http://www.voidmaintechnologies.com">info</a>
			</p>
		</div>
	</div>
</body>
</html>


