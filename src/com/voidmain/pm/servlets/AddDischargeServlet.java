package com.voidmain.pm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;
import com.voidmain.pm.form.DischargeForm;


@WebServlet("/AddDischargeServlet")
public class AddDischargeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DischargeForm dischargeForm=new DischargeForm();
		
		dischargeForm.setPatientName(request.getParameter("patientname"));
		dischargeForm.setDischargeDate(request.getParameter("date"));
		dischargeForm.setDischargeSummary(request.getParameter("summary"));
		
		dischargeForm.setHospitalName((String)request.getSession().getAttribute("username"));

		if(AppDAO.addDischargeForm(dischargeForm)==1)
		{
			response.sendRedirect("adddischarge.jsp?status=success");
		}
		else
		{
			response.sendRedirect("adddischarge.jsp?status=Exist");
		}
	}
}
