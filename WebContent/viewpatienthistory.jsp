<!DOCTYPE HTML>
<%@page import="java.util.Iterator"%>
<%@page import="com.voidmain.pm.form.DischargeForm"%>
<%@page import="com.voidmain.pm.form.SurgaryForm"%>
<%@page import="com.voidmain.pm.form.DailyForm"%>
<%@page import="com.voidmain.pm.form.CurrentStatusForm"%>
<%@page import="com.voidmain.pm.dao.AppDAO"%>
<%@page import="com.voidmain.pm.form.Reports"%>
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
					String status = request.getParameter("status");

					if (status != null) {
				%>
				<h1><%=status%></h1>
				<%
					} else {
				%>
				<h1>Add Reports</h1>
				<%
					}
				%>

				<form action="viewpatienthistory.jsp" name="ff"
					onsubmit="return check()">

					<div class="form_settings">

						<p>
							<span>Enter Patient ID</span><input type="text"
								name="patientname" />
						</p>

						<p style="padding-top: 15px">
							<span>&nbsp;</span><input class="submit" type="submit"
								name="contact_submitted" value="submit" />
						</p>
					</div>
				</form>

				<%
					String patientName = request.getParameter("patientname");

					if (patientName != null) {
						
						List<Reports> reports = AppDAO.getReportsByPatient(patientName);
						
						CurrentStatusForm currentStatus = AppDAO
								.getCurrentStatusFormByid(patientName);
						
						List<DailyForm> dailyForms = AppDAO
								.getDailyFormByPatient(patientName);
						
						List<SurgaryForm> surgaryForms = AppDAO
								.getSurgaryFormByPatient(patientName);
						
						List<DischargeForm> dischargeForms = AppDAO
								.getDischargeFormByPatient(patientName);
				%>
				
				<h1>Current Status</h1>
				<table class="imagetable" align="center">
					<tr>
						<th><%=currentStatus.getCurrentStatus()%></th>
						<th><%=currentStatus.getCurrentStatusdate()%></th>
						<th><%=currentStatus.getHospitalName()%></th>
					</tr>
				</table>
				<br/><hr/><hr/><h1>Surgery Details</h1>
				<table class="imagetable">
					<tr>
						<th>SurgeryName</th>
						<th>Date</th>
						<th>Hospital Name</th>
					</tr>
					<%
						Iterator<SurgaryForm> iterator1 = surgaryForms.iterator();

							while (iterator1.hasNext()) {

								SurgaryForm surgaryForm = iterator1.next();
					%>
					<tr>
						<td><%=surgaryForm.getSurgaryName()%></td>
						<td><%=surgaryForm.getDate()%></td>
						<td><%=surgaryForm.getHospitalName()%></td>
					</tr>
					<%
							}
						%>

				</table>
				
				<br/><hr/><hr/><h1>Reports</h1>
				
				<table class="imagetable">
					<tr>
						<th>Report Name</th>
						<th>Date</th>
						<th>Hospital Name</th>
						<th>Download</th>
					</tr>
					<%
						Iterator<Reports> iterator2 = reports.iterator();

							while (iterator2.hasNext()) {

								Reports report = iterator2.next();
					%>
					<tr>
						<td><%=report.getReportName()%></td>
						<td><%=report.getDate()%></td>
						<td><%=report.getHospitalName()%></td>
						<td><a href="DownloadServlet?rid=<%=report.getReportId()%>">download</a></td>
					</tr>
					<%
							}
						%>

				</table>
				
				<br/><hr/><hr/><h1>Discharge Details</h1>
				
				<table class="imagetable">
					<tr>
						<th>Date</th>
						<th>Discharge Summary</th>
						<th>Hospital Name</th>
					</tr>
					<%
								Iterator<DischargeForm> iterator3 =dischargeForms.iterator();

								while (iterator3.hasNext()) {
									
									DischargeForm dischargeForm= iterator3.next();
									
							%>
					<tr>
						<td><%=dischargeForm.getDischargeDate()%></td>
						<td><%=dischargeForm.getDischargeSummary()%></td>
						<td><%=dischargeForm.getHospitalName()%></td>

					</tr>
					<%
							}
					%>
				</table>
				
				<br/><hr/><hr/><h1>Medicine Details</h1>
				
				<table class="imagetable">
					<tr>

						<th>Date</th>
						<th>Morning</th>
						<th>Afternoon</th>
						<th>Night</th>
						<th>Doctor Name</th>
						<th>Hospital Name</th>
					</tr>
					<%
								Iterator<DailyForm> iterator4 =dailyForms.iterator();

								while (iterator4.hasNext()) {
									
									DailyForm dailyForm = iterator4.next();
									
							%>
					<tr>
						<td><%=dailyForm.getDate()%></td>
						<td><%=dailyForm.getMorning()%></td>
						<td><%=dailyForm.getAfternoon()%></td>
						<td><%=dailyForm.getNight()%></td>
						<td><%=dailyForm.getDoctorName()%></td>
						<td><%=dailyForm.getHospitalName()%></td>
					</tr>
					<%
							}
					%>
				</table>

				<%
					}
				%>

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
