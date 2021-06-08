package com.voidmain.pm.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.voidmain.pm.dao.AppDAO;
import com.voidmain.pm.form.Reports;
import com.voidmain.pm.service.SimpleFTPClient;
import com.voidmain.pm.util.AppUtil;


@WebServlet("/AddReportsServlets")
public class AddReportsServlets extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Reports reports=new Reports();
		
		reports.setHospitalName((String)request.getSession().getAttribute("username"));

		String uploadFilename = "";

		String path =AppUtil.FILE_PATH;

		boolean isUploaded = false;

		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if(isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				// Parse the request
				List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));

				for (FileItem item : items) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						//this will be true if file field is found in the List
						uploadFilename = item.getName();
						
						  reports.setFileName(uploadFilename);
						
						  path=path+uploadFilename;

						try {
							File savedFile = new File(path);
							//out.print(savedFile.getAbsolutePath());
							item.write(savedFile);
							isUploaded = true;
							
							
							SimpleFTPClient client = new SimpleFTPClient();
					
							client.setHost("ftp.drivehq.com");
							client.setUser("Batch13");
							client.setPassword("Majorproject123*");
							
							client.setRemoteFile(uploadFilename);

							if(client.connect())
							{
								String encpath="C:\\Users\\nithi\\OneDrive\\Desktop\\appuploads\\"+uploadFilename;

								FileOutputStream myos=new FileOutputStream(encpath);
								FileInputStream is=new FileInputStream(savedFile);

								Random r=new Random();

								String key="";

								String pattern="ABCDEFGHIJLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

								for(int i=0;i<9;i++)
								{
									key=key+pattern.charAt(r.nextInt(60));

								}

								try {

									SimpleFTPClient.encrypt(is, myos,key);

								} catch (Throwable e) {

									e.printStackTrace();
								}

								if(client.uploadFile(new FileInputStream(encpath)))
								{
									reports.setKey(key);
								}
							}

							
						} catch(Exception e) {
							isUploaded = false;
							e.printStackTrace();
						}
					} else {
						
						String fieldname = item.getFieldName();
						String fieldvalue = item.getString();
						
						if(fieldname.equals("patientname"))
						{
							reports.setPatientName(fieldvalue);
						}
						else
						{
							if(fieldname.equals("date"))
							{
								reports.setDate(fieldvalue);
							}
							else
							{
								if(fieldname.equals("reportname"))
								{
									reports.setReportName(fieldvalue);
								}
							}
						}
					}
				}
			}//try
			catch (FileUploadException e) {
				e.printStackTrace();
			} 

			if(isUploaded){
				
				String status=AppDAO.addReports(reports)==1?"success":"success";

				if(status.equals("success"))
				{
					response.sendRedirect("addreport.jsp?status=success");
				}
				else
				{
					response.sendRedirect("addreport.jsp?status=failed");
				}
			}
		}
	}
}