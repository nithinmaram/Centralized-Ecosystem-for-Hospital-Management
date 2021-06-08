package com.voidmain.pm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;

@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String username=(String)request.getSession().getAttribute("username");
		
		String oldpassword=request.getParameter("oldpassword");
		String newpassword=request.getParameter("newpassword");

		if(AppDAO.updatePassword(username, oldpassword,newpassword)==1)
		{
			
			if(AppDAO.getUserType(username).equals("admin"))
			{
				response.sendRedirect("adminhome.jsp");
			}
			else
			{
				if(AppDAO.getUserType(username).equals("patient"))
				{
					response.sendRedirect("patienthome.jsp");
				}
				else
				{
					if(AppDAO.getUserType(username).equals("hospital"))
					{
						response.sendRedirect("hospitalhome.jsp");
					}
				}
			}
		}	
	}
}
