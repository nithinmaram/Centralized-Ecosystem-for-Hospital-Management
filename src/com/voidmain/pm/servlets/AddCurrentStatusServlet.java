package com.voidmain.pm.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;
import com.voidmain.pm.form.CurrentStatusForm;


@WebServlet("/AddCurrentStatusServlet")
public class AddCurrentStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CurrentStatusForm currentStatusForm=new CurrentStatusForm();

		currentStatusForm.setPatientName(request.getParameter("patientname"));
		currentStatusForm.setCurrentStatus(request.getParameter("currentstatus"));
		currentStatusForm.setCurrentStatusdate(new Date().toString());
		
		currentStatusForm.setHospitalName((String)request.getSession().getAttribute("username"));

		if(AppDAO.addCurrentStatusForm(currentStatusForm)==1)
		{
			response.sendRedirect("addcurrentstatus.jsp?status=success");
		}
		else
		{
			response.sendRedirect("addcurrentstatus.jsp?status=Exist");
		}
	}
}
