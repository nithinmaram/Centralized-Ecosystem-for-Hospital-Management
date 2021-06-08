package com.voidmain.pm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username=request.getParameter("username").trim();
		
		String role=AppDAO.isValidUser(username,request.getParameter("password"));
		
		if(role!=null && role!="")
		{
			request.getSession().setAttribute("username",username);
			
			if(role.equals("admin"))
			{
				response.sendRedirect("adminhome.jsp");
			}
			else
			{
				if(role.equals("patient"))
				{
					response.sendRedirect("patienthome.jsp");
				}
				else
				{
					if(role.equals("hospital"))
					{
						response.sendRedirect("hospitalhome.jsp");
					}
				}
			}
		}
		else
		{
			response.sendRedirect("login.jsp?status=invalid credentials");
		}	
	}
}
