package com.voidmain.pm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;
import com.voidmain.pm.form.SurgaryForm;


@WebServlet("/AddSurgeryServlet")
public class AddSurgeryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SurgaryForm surgaryForm=new SurgaryForm();
		
		surgaryForm.setSurgaryName(request.getParameter("surgeryname"));
		surgaryForm.setPatientName(request.getParameter("patientname"));
		surgaryForm.setDate(request.getParameter("date"));
		surgaryForm.setHospitalName((String)request.getSession().getAttribute("username"));
		
		if(AppDAO.addSurgaryForm(surgaryForm)==1)
		{
			response.sendRedirect("addsurgery.jsp?status=success");
		}
		else
		{
			response.sendRedirect("addsurgery.jsp?status=Exist");
		}
	}
}
