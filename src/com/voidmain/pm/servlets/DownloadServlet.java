package com.voidmain.pm.servlets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voidmain.pm.dao.AppDAO;
import com.voidmain.pm.form.Reports;
import com.voidmain.pm.service.SimpleFTPClient;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {

	SimpleFTPClient client;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try {

			String rid=request.getParameter("rid");

			Reports report=AppDAO.getReportsByid(Integer.parseInt(rid));

			String path = "C:\\Users\\nithi\\OneDrive\\Desktop\\appdownloads\\";

			client = new SimpleFTPClient();
			client.setHost("ftp.drivehq.com");
			client.setUser("Batch13");
			client.setPassword("Majorproject123*");
			client.setRemoteFile(report.getFileName());

			if(client.connect())
			{
				String key=report.getKey();
				
				System.out.println("Key :"+key);
				System.out.println("File Name :"+report.getFileName());

				if (client.downloadFile(path+report.getFileName())) {

					FileInputStream fis=new FileInputStream(path+report.getFileName());

					FileOutputStream fos=new FileOutputStream(path+"new"+report.getFileName());

					try {
						SimpleFTPClient.decrypt(fis, fos,key);
					} catch (Throwable e) {
						e.printStackTrace();
					}

					response.setContentType("text/html");    
					response.setContentType("APPLICATION/OCTET-STREAM");   
					response.setHeader("Content-Disposition","attachment; filename=\"" +report.getFileName()+ "\"");   

					FileInputStream fileInputStream = new FileInputStream(path+"new"+report.getFileName());  

					int i;   
					while ((i=fileInputStream.read()) != -1) {  
						out.write(i);   
					}   

					fileInputStream.close();   
					out.close();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
