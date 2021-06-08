package com.voidmain.pm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;
import com.voidmain.pm.form.DailyForm;


@WebServlet("/AddDialyMedisineServlet")
public class AddDialyMedisineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DailyForm dailyForm=new DailyForm();

		dailyForm.setPatientName(request.getParameter("patientname"));
		dailyForm.setDoctorName(request.getParameter("doctorname"));
		dailyForm.setMorning(request.getParameter("morning"));
		dailyForm.setAfternoon(request.getParameter("afternoon"));
		dailyForm.setNight(request.getParameter("night"));
		dailyForm.setDate(request.getParameter("date"));

		dailyForm.setHospitalName((String)request.getSession().getAttribute("username"));

		if(AppDAO.addDailyForm(dailyForm)==1)
		{
			response.sendRedirect("adddialymedicine.jsp?status=success");
		}
		else
		{
			response.sendRedirect("adddialymedicine.jsp?status=Exist");
		}
	}
}
