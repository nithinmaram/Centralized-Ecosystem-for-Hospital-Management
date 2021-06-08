<!DOCTYPE HTML>
<%@page import="com.voidmain.pm.form.Report"%>
<%@page import="com.voidmain.pm.form.Message"%>
<%@page import="com.voidmain.pm.form.User"%>
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
					<li><a class="current" href="patienthome.jsp">Home</a></li>
					<li class="hvr-sweep-to-bottom"><a href="search.jsp">Search Patient</a></li>
					<li class="hvr-sweep-to-bottom"><a href="inbox.jsp">Inbox</a></li>
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
				<h1>Search Patients</h1>
				<%
					}
				%>

				<form action="search.jsp" name="ff"
					onsubmit="return check()">

					<div class="form_settings">

						<p>
							<span>Enter keyword</span><input type="text"
								name="query" />
						</p>

						<p style="padding-top: 15px">
							<span>&nbsp;</span><input class="submit" type="submit"
								name="contact_submitted" value="submit" />
						</p>
					</div>
				</form>

				<%
					String query = request.getParameter("query");

					if (query != null) {
						
						List<User> userForms= AppDAO.getPatientsByQuery(query);
				%>
				
				<h1>Patients</h1>
				<table class="imagetable">
					<tr>
						<th>Patient Name</th>
						<th>Address</th>
						<th>Mobile</th>
						<th>Email</th>
						<th>Profile</th>
						<th>Send Query</th>
						<th>Report</th>
					</tr>
					<%
								Iterator<User> iterator = userForms.iterator();

								while (iterator.hasNext()) {
									
									User userForm = iterator.next();
							%>
							<tr>
								<td><%=userForm.getUserName()%></td>
								<td><%=userForm.getAddress()%></td>
								<td><%=userForm.getMobile()%></td>
								<td><%=userForm.getEmail()%></td>
								<td><a
									href="viewpatientprofile.jsp?username=<%=userForm.getUserName()%>">View</a>
								</td>
								<td><a
									href="search.jsp?receiver=<%=userForm.getUserName()%>">send</a></td>
								<td><a
									href="search.jsp?patientname=<%=userForm.getUserName()%>">Report</a></td>
							</tr>
							<%
								}
							%>		
							
						</table>
				<%
					}
				%>
				
				<%
								String receiver=request.getParameter("receiver");
								
								if(receiver!=null)
								{
							%>
									<form action="search.jsp" name="ff">
					
										<div class="form_settings">
											
											<input type="hidden" name="receiver" value="<%=receiver%>" />
											<p>
												<span>Enter Question</span><input type="text"
													name="question" />
											</p>
					
											<p style="padding-top: 15px">
												<span>&nbsp;</span><input class="submit" type="submit"
													name="contact_submitted" value="submit" />
											</p>
										</div>
									</form>
							<%		
								}
							%>
							
							<%
								receiver=request.getParameter("receiver");
								String question=request.getParameter("question");
								
								if(receiver!=null && question!=null)
								{
									Message message1=new Message();
									
									message1.setUsername((String)request.getSession().getAttribute("username"));
									message1.setReceiver(receiver);
									message1.setMessage(question);
									
									int result=AppDAO.addMessage(message1);
									
									if(result==1)
									{
										response.sendRedirect("search.jsp?status=success");
									}
									else
									{
										response.sendRedirect("search.jsp?status=failed");
									}
								}
							%>
							
							<%
							
								String patientname=request.getParameter("patientname");
								
								if(patientname!=null)
								{
									Report report=new Report();
									
									report.setReportedBy((String)request.getSession().getAttribute("username"));
									report.setPatientName(patientname);
									
									int result=AppDAO.addReport(report);
									
									if(result==1)
									{
										response.sendRedirect("search.jsp?status=success");
									}
									else
									{
										response.sendRedirect("search.jsp?status=failed");
									}
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
