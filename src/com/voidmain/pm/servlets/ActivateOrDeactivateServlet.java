package com.voidmain.pm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;

@WebServlet({"/ActivateOrDeactivateServlet"})

public class ActivateOrDeactivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		if(AppDAO.activateUser(request.getParameter("hospitalname"),request.getParameter("status"))==1)
		{
			response.sendRedirect("viewhospitals.jsp?status=success");
		}
		else
		{
			response.sendRedirect("viewhospitals.jsp?status=failed");
		}
	}
}
