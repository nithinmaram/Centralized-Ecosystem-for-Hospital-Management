<!DOCTYPE HTML>
<%@page import="com.voidmain.pm.dao.AppDAO"%>
<%@page import="com.voidmain.pm.form.Message"%>
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

					<li><a class="current" href="patienthome.jsp">Home</a></li>
					<li class="hvr-sweep-to-bottom"><a href="search.jsp">Search Patient</a></li>
					<li class="hvr-sweep-to-bottom"><a href="inbox.jsp">Inbox</a></li>
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
				<h1>View Messages</h1>
				<%
					}
				%>

				<table class="imagetable">
					<tr>
						<th>ID</th>
						<th>Sender</th>
						<th>Receiver</th>
						<th>Message</th>
					</tr>
					<%
						List<Message> messages = AppDAO.getMessagesByReceiver((String)request.getSession().getAttribute("username"));

						Iterator<Message> iterator = messages.iterator();

						while (iterator.hasNext()) {

							Message message = iterator.next();
					%>
					<tr>
						<td><%=message.getId()%></td>
						<td><%=message.getUsername()%></td>
						<td><%=message.getReceiver()%></td>
						<td><%=message.getMessage()%></td>
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


