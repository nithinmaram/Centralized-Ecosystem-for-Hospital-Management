package com.voidmain.pm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Request;

import com.voidmain.pm.form.CurrentStatusForm;
import com.voidmain.pm.form.DailyForm;
import com.voidmain.pm.form.DischargeForm;
import com.voidmain.pm.form.Message;
import com.voidmain.pm.form.Report;
import com.voidmain.pm.form.Reports;
import com.voidmain.pm.form.SurgaryForm;
import com.voidmain.pm.form.User;

public class AppDAO {

	public static String isValidUser(String username,String password)
	{
		String roleId="";
		
		String status="activated";

		try {

			ResultSet rs=MyConnection.getConnection().createStatement().executeQuery("select role_id from login where username='"+username+"' and password='"+password+"' and status='"+status+"'");

			while (rs.next()) {

				roleId=rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roleId;
	}

	public static String getUserType(String userName)
	{
		String role_id="";

		try {

			ResultSet rs=MyConnection.getConnection().createStatement().executeQuery("select role_id from login where username='"+userName+"'");

			while(rs.next())
			{
				role_id=rs.getString("role_id");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return role_id;
	}
	
	public static String getUserStatus(String userName)
	{
		String status="";

		try {

			ResultSet rs=MyConnection.getConnection().createStatement().executeQuery("select status from login where username='"+userName+"'");

			while(rs.next())
			{
				status=rs.getString("status");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return status;
	}
	
	public static int updatePassword(String username,String oldPassword,String newPassword)
	{
		int result=0;

		try {
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("update login set password=? where username=? and password=?");
			ps.setString(1,newPassword);
			ps.setString(2,username);
			ps.setString(3,oldPassword);

			result=ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static int isUserRegistred(String username)
	{
		int result=0;

		try {

			ResultSet rs=MyConnection.getConnection().createStatement().executeQuery("select count(*) from login where username='"+username+"'");

			while (rs.next()) {

				result=rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static int userRegistration(User userForm)
	{
		int result=0; 

		try {

			if(isUserRegistred(userForm.getUserName())==0)
			{
				PreparedStatement ps2=MyConnection.getConnection().prepareStatement("insert into login value(?,?,?,?)");

				ps2.setString(1,userForm.getUserName());
				ps2.setString(2,userForm.getPassword());
				ps2.setString(3,userForm.getType());
				
				if(userForm.getType().equals("patient"))
				{
					ps2.setString(4,"activated");
				}
				else
				{
					ps2.setString(4,"waiting");
				}
				
				if(ps2.executeUpdate()==1)
				{
					PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into registration values(?,?,?,?,?)");
					
					ps.setString(1,userForm.getUserName());
					ps.setString(2,userForm.getAddress());
					ps.setString(3,userForm.getMobile());
					ps.setString(4,userForm.getEmail());
					ps.setString(5,userForm.getType());

					if(ps.executeUpdate()==1)
					{
						result=2;
					}
					else
					{
						MyConnection.getConnection().createStatement().executeUpdate("delete from login where username='"+userForm.getUserName()+"'");
					}
				}
			}
			else
			{
				result=1;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<User> getUsersByType(String type)
	{
		List<User> usersList=new ArrayList<User>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from registration where type='"+type+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				User user=new User();
				
				user.setUserName(rs.getString("username"));
				user.setAddress(rs.getString("address"));
				user.setMobile(rs.getString("mobile"));
				user.setEmail(rs.getString("email"));
				user.setType(rs.getString("type"));

				usersList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersList;
	}
	
	public static User getUserByUserName(String userName)
	{
		User user=new User();
		
		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from registration where username='"+userName+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {
				
				user.setUserName(rs.getString("username"));
				user.setAddress(rs.getString("address"));
				user.setMobile(rs.getString("mobile"));
				user.setEmail(rs.getString("email"));
				user.setType(rs.getString("type"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public static int activateUser(String username,String status)
	{
		int result=0;

		try {

			result=MyConnection.getConnection().createStatement().executeUpdate("update login set status='"+status+"' where username='"+username+"'");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	//=================================================================
	
	
	public static int addReports(Reports reports)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into reports values(?,?,?,?,?,?,?)");

			ps.setInt(1,0);
			ps.setString(2,reports.getPatientName());
			ps.setString(3,reports.getDate());
			ps.setString(4,reports.getHospitalName());
			ps.setString(5,reports.getReportName());
			ps.setString(6,reports.getFileName());
			ps.setString(7,reports.getKey());

			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static int deleteReports(int reportId)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("delete from reports where reportid='"+reportId+"'");
			
			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<Reports> getReports()
	{
		List<Reports> reportsList=new ArrayList<Reports>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from reports");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				Reports reports=new Reports();
			
				reports.setReportId(rs.getInt(1));
				reports.setPatientName(rs.getString(2));
				reports.setDate(rs.getString(3));
				reports.setHospitalName(rs.getString(4));
				reports.setReportName(rs.getString(5));
				reports.setFileName(rs.getString(6));
				reports.setKey(rs.getString(7));

				reportsList.add(reports);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reportsList;
	}
	
	public static Reports getReportsByid(int rid)
	{
		Reports reports=new Reports();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from reports where reportid='"+rid+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				reports.setReportId(rs.getInt(1));
				reports.setPatientName(rs.getString(2));
				reports.setDate(rs.getString(3));
				reports.setHospitalName(rs.getString(4));
				reports.setReportName(rs.getString(5));
				reports.setFileName(rs.getString(6));
				reports.setKey(rs.getString(7));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reports;
	}
	
	public static List<Reports> getReportsByPatient(String patientName)
	{
		List<Reports> reportsList=new ArrayList<Reports>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM reports where patientname='"+patientName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				Reports reports=new Reports();
				
				reports.setReportId(rs.getInt(1));
				reports.setPatientName(rs.getString(2));
				reports.setDate(rs.getString(3));
				reports.setHospitalName(rs.getString(4));
				reports.setReportName(rs.getString(5));
				reports.setFileName(rs.getString(6));
				reports.setKey(rs.getString(7));
				
				reportsList.add(reports);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reportsList;
	} 
	
	
	public static List<Reports> getPatientReportsByHospital(String patientName,String hospitalName)
	{
		List<Reports> reportsList=new ArrayList<Reports>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM reports where patientname='"+patientName+"' and hospitalname='"+hospitalName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				Reports reports=new Reports();
				
				reports.setReportId(rs.getInt(1));
				reports.setPatientName(rs.getString(2));
				reports.setDate(rs.getString(3));
				reports.setHospitalName(rs.getString(4));
				reports.setReportName(rs.getString(5));
				reports.setFileName(rs.getString(6));
				reports.setKey(rs.getString(7));
				
				reportsList.add(reports);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reportsList;
	} 
	
	//================================ reports ================================================
	
	public static int addDischargeForm(DischargeForm dischargeForm)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into discharge values(?,?,?,?,?)");
			
			ps.setInt(1,0);
			ps.setString(2,dischargeForm.getPatientName());
			ps.setString(3,dischargeForm.getDischargeDate());
			ps.setString(4,dischargeForm.getDischargeSummary());
			ps.setString(5,dischargeForm.getHospitalName());
			
			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static int deleteDischargeForm(int dischargeId)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("delete from discharge where dischargeid='"+dischargeId+"'");
			
			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<DischargeForm> getDischargeForm()
	{
		List<DischargeForm> dischargeFormList=new ArrayList<DischargeForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from discharge");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				DischargeForm dischargeForm=new DischargeForm();
				
				dischargeForm.setDischargeId(rs.getInt(1));
				dischargeForm.setPatientName(rs.getString(2));
				dischargeForm.setDischargeDate(rs.getString(3));
				dischargeForm.setDischargeSummary(rs.getString(4));
				dischargeForm.setHospitalName(rs.getString(5));

				dischargeFormList.add(dischargeForm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dischargeFormList;
	}
	
	public static DischargeForm getDischargeFormByid(int dischargeId)
	{
		DischargeForm dischargeForm=new DischargeForm();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from discharge where dischargeid='"+dischargeId+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				dischargeForm.setDischargeId(rs.getInt(1));
				dischargeForm.setPatientName(rs.getString(2));
				dischargeForm.setDischargeDate(rs.getString(3));
				dischargeForm.setDischargeSummary(rs.getString(4));
				dischargeForm.setHospitalName(rs.getString(5));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dischargeForm;
	}
	
	public static List<DischargeForm> getDischargeFormByPatient(String patientName)
	{
		List<DischargeForm> dischargeFormList=new ArrayList<DischargeForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM discharge where patientname='"+patientName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				DischargeForm dischargeForm=new DischargeForm();
				
				dischargeForm.setDischargeId(rs.getInt(1));
				dischargeForm.setPatientName(rs.getString(2));
				dischargeForm.setDischargeDate(rs.getString(3));
				dischargeForm.setDischargeSummary(rs.getString(4));
				dischargeForm.setHospitalName(rs.getString(5));
				
				dischargeFormList.add(dischargeForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dischargeFormList;
	} 
	
	
	public static List<DischargeForm> getPatientDischargeFormByHospital(String patientName,String hospitalName)
	{
		List<DischargeForm> dischargeFormList=new ArrayList<DischargeForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM discharge where patientname='"+patientName+"' and hospitalname='"+hospitalName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				DischargeForm dischargeForm=new DischargeForm();
				
				dischargeForm.setDischargeId(rs.getInt(1));
				dischargeForm.setPatientName(rs.getString(2));
				dischargeForm.setDischargeDate(rs.getString(3));
				dischargeForm.setDischargeSummary(rs.getString(4));
				dischargeForm.setHospitalName(rs.getString(5));
				
				dischargeFormList.add(dischargeForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dischargeFormList;
	}
	
	//======================================== DischargeForm ==============================
	
	public static int addDailyForm(DailyForm dailyForm)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into daily values(?,?,?,?,?,?,?,?)");
			
			ps.setInt(1,0);
			ps.setString(2,dailyForm.getPatientName());
			ps.setString(3,dailyForm.getDoctorName());
			ps.setString(4,dailyForm.getMorning());
			ps.setString(5,dailyForm.getAfternoon());
			ps.setString(6,dailyForm.getNight());
			ps.setString(7,dailyForm.getDate());
			ps.setString(8,dailyForm.getHospitalName());

			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static int deleteDailyForm(int dailyId)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("delete from daily where dailyid='"+dailyId+"'");
			
			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<DailyForm> getDailyForm()
	{
		List<DailyForm> dailyFormList=new ArrayList<DailyForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from daily");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				DailyForm dailyForm=new DailyForm();
			
				dailyForm.setDialyId(rs.getInt(1));
				dailyForm.setPatientName(rs.getString(2));
				dailyForm.setDoctorName(rs.getString(3));
				dailyForm.setMorning(rs.getString(4));
				dailyForm.setAfternoon(rs.getString(5));
				dailyForm.setNight(rs.getString(6));
				dailyForm.setDate(rs.getString(7));
				dailyForm.setHospitalName(rs.getString(8));
				
				dailyFormList.add(dailyForm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dailyFormList;
	}
	
	public static DailyForm getDailyFormByid(int pid)
	{
		DailyForm dailyForm=new DailyForm();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from daily where dailyid='"+pid+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				dailyForm.setDialyId(rs.getInt(1));
				dailyForm.setPatientName(rs.getString(2));
				dailyForm.setDoctorName(rs.getString(3));
				dailyForm.setMorning(rs.getString(4));
				dailyForm.setAfternoon(rs.getString(5));
				dailyForm.setNight(rs.getString(6));
				dailyForm.setDate(rs.getString(7));
				dailyForm.setHospitalName(rs.getString(8));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dailyForm;
	}
	
	public static List<DailyForm> getDailyFormByPatient(String patientName)
	{
		List<DailyForm> dailyFormList=new ArrayList<DailyForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM daily where patientname='"+patientName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				DailyForm dailyForm=new DailyForm();
				
				dailyForm.setDialyId(rs.getInt(1));
				dailyForm.setPatientName(rs.getString(2));
				dailyForm.setDoctorName(rs.getString(3));
				dailyForm.setMorning(rs.getString(4));
				dailyForm.setAfternoon(rs.getString(5));
				dailyForm.setNight(rs.getString(6));
				dailyForm.setDate(rs.getString(7));
				dailyForm.setHospitalName(rs.getString(8));
				
				dailyFormList.add(dailyForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dailyFormList;
	} 
	
	
	public static List<DailyForm> getPatientDailyFormByHospital(String patientName,String hospitalName)
	{
		List<DailyForm> dailyFormList=new ArrayList<DailyForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM daily where patientname='"+patientName+"' and hospitalname='"+hospitalName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				DailyForm dailyForm=new DailyForm();
				
				dailyForm.setDialyId(rs.getInt(1));
				dailyForm.setPatientName(rs.getString(2));
				dailyForm.setDoctorName(rs.getString(3));
				dailyForm.setMorning(rs.getString(4));
				dailyForm.setAfternoon(rs.getString(5));
				dailyForm.setNight(rs.getString(6));
				dailyForm.setDate(rs.getString(7));
				dailyForm.setHospitalName(rs.getString(8));
				
				dailyFormList.add(dailyForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dailyFormList;
	}
	
	//================================  Daily =============================================
	
	public static int addCurrentStatusForm(CurrentStatusForm currentStatusForm)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into currentStatus values(?,?,?,?)");
			
			ps.setString(1,currentStatusForm.getPatientName());
			ps.setString(2,currentStatusForm.getCurrentStatus());
			ps.setString(3,currentStatusForm.getCurrentStatusdate());
			ps.setString(4,currentStatusForm.getHospitalName());

			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static int deleteCurrentStatusForm(String patientname)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("delete from currentStatus where patientname='"+patientname+"'");
			
			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<CurrentStatusForm> getCurrentStatusForm()
	{
		List<CurrentStatusForm> currentStatusFormList=new ArrayList<CurrentStatusForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from currentStatus");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				CurrentStatusForm currentStatusForm=new CurrentStatusForm();

				currentStatusForm.setPatientName(rs.getString(1));
				currentStatusForm.setCurrentStatus(rs.getString(2));
				currentStatusForm.setCurrentStatusdate(rs.getString(3));
				currentStatusForm.setHospitalName(rs.getString(4));

				currentStatusFormList.add(currentStatusForm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentStatusFormList;
	}
	
	public static CurrentStatusForm getCurrentStatusFormByid(String patientName)
	{
		CurrentStatusForm currentStatusForm=new CurrentStatusForm();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from currentStatus where patientname='"+patientName+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				currentStatusForm.setPatientName(rs.getString(1));
				currentStatusForm.setCurrentStatus(rs.getString(2));
				currentStatusForm.setCurrentStatusdate(rs.getString(3));
				currentStatusForm.setHospitalName(rs.getString(4));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentStatusForm;
	}
	
	public static List<CurrentStatusForm> getPatientCurrentStatusFormByHospital(String patientName,String hospitalName)
	{
		List<CurrentStatusForm> currentStatusFormList=new ArrayList<CurrentStatusForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM currentStatus where patientname='"+patientName+"' and hospitalname='"+hospitalName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				CurrentStatusForm currentStatusForm=new CurrentStatusForm();
				
				currentStatusForm.setPatientName(rs.getString(1));
				currentStatusForm.setCurrentStatus(rs.getString(2));
				currentStatusForm.setCurrentStatusdate(rs.getString(3));
				currentStatusForm.setHospitalName(rs.getString(4));
				
				currentStatusFormList.add(currentStatusForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentStatusFormList;
	}
	
	//========================= current status =============================================
	
	public static int addSurgaryForm(SurgaryForm surgaryForm)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into surgery values(?,?,?,?,?)");

			ps.setInt(1,0);
			ps.setString(2,surgaryForm.getSurgaryName());
			ps.setString(3,surgaryForm.getPatientName());
			ps.setString(4,surgaryForm.getDate());
			ps.setString(5,surgaryForm.getHospitalName());

			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static int deleteSurgaryForm(int surgeryid)
	{
		int result=0; 

		try {
			
			PreparedStatement ps=MyConnection.getConnection().prepareStatement("delete from surgery where surgeryid='"+surgeryid+"'");
			
			result=ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<SurgaryForm> getSurgaryForm()
	{
		List<SurgaryForm> surgaryFormList=new ArrayList<SurgaryForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from surgery");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				SurgaryForm surgaryForm=new SurgaryForm();

				surgaryForm.setSurgaryId(rs.getInt(1));
				surgaryForm.setSurgaryName(rs.getString(2));
				surgaryForm.setPatientName(rs.getString(3));
				surgaryForm.setDate(rs.getString(4));
				surgaryForm.setHospitalName(rs.getString(5));

				surgaryFormList.add(surgaryForm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return surgaryFormList;
	}
	
	public static SurgaryForm getSurgaryFormByid(int pid)
	{
		SurgaryForm surgaryForm=new SurgaryForm();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from surgery where surgeryid='"+pid+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				surgaryForm.setSurgaryId(rs.getInt(1));
				surgaryForm.setSurgaryName(rs.getString(2));
				surgaryForm.setPatientName(rs.getString(3));
				surgaryForm.setDate(rs.getString(4));
				surgaryForm.setHospitalName(rs.getString(5));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return surgaryForm;
	}
	
	public static List<SurgaryForm> getSurgaryFormByPatient(String patientName)
	{
		List<SurgaryForm> surgaryFormList=new ArrayList<SurgaryForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM surgery where patientname='"+patientName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				SurgaryForm surgaryForm=new SurgaryForm();
				
				surgaryForm.setSurgaryId(rs.getInt(1));
				surgaryForm.setSurgaryName(rs.getString(2));
				surgaryForm.setPatientName(rs.getString(3));
				surgaryForm.setDate(rs.getString(4));
				surgaryForm.setHospitalName(rs.getString(5));
				
				surgaryFormList.add(surgaryForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return surgaryFormList;
	} 
	
	
	public static List<SurgaryForm> getPatientSurgaryFormByHospital(String patientName,String hospitalName)
	{
		List<SurgaryForm> surgaryFormList=new ArrayList<SurgaryForm>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("SELECT * FROM surgery where patientname='"+patientName+"' and hospitalname='"+hospitalName+"'");

			ResultSet rs =ps.executeQuery();

			while (rs.next()) {

				SurgaryForm surgaryForm=new SurgaryForm();
				
				surgaryForm.setSurgaryId(rs.getInt(1));
				surgaryForm.setSurgaryName(rs.getString(2));
				surgaryForm.setPatientName(rs.getString(3));
				surgaryForm.setDate(rs.getString(4));
				surgaryForm.setHospitalName(rs.getString(5));
				
				surgaryFormList.add(surgaryForm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return surgaryFormList;
	}
	
	public static List<User> getPatientsByQuery(String query)
	{
		List<User> usersList=new ArrayList<User>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from surgery where surgeryname='"+query+"' or hospitalname='"+query+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				String name=rs.getString("patientname");
				System.out.println("surgery"+name);
				usersList.add(getUserByUserName(name));
			}
			
			PreparedStatement ps1=MyConnection.getConnection().prepareStatement("select * from daily where morning='"+query+"' or afternoon='"+query+"' or night='"+query+"' or hospitalname='"+query+"'");

			ResultSet rs1=ps1.executeQuery();

			while (rs1.next()) {

				String name=rs1.getString("patientname");
				System.out.println("daily"+name);
				usersList.add(getUserByUserName(name));
			}
			
			PreparedStatement ps2=MyConnection.getConnection().prepareStatement("select * from currentstatus where status='"+query+"' or hospitalname='"+query+"'");

			ResultSet rs2=ps2.executeQuery();

			while (rs2.next()) {

				String name=rs2.getString("patientname");
				System.out.println("current status"+name);
				usersList.add(getUserByUserName(name));
			}
			
			String type="patient";
			
			PreparedStatement ps3=MyConnection.getConnection().prepareStatement("select * from registration where type='"+type+"' and address='"+query+"'");

			ResultSet rs3=ps3.executeQuery();

			while (rs3.next()) {

				String name=rs3.getString("username");
				System.out.println("registration"+name);
				usersList.add(getUserByUserName(name));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usersList;
	}
	
	public static int addMessage(Message message)
	{
		int result=0; 

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into querys values(?,?,?,?)");
		
			ps.setInt(1,0);
			ps.setString(2,message.getUsername());
			ps.setString(3,message.getReceiver());
			ps.setString(4,message.getMessage());

			result=ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<Message> getMessagesByReceiver(String username)
	{
		List<Message> messagesList=new ArrayList<Message>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from querys where receiver='"+username+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				Message message=new Message();
				
				message.setId(rs.getInt("id"));
				message.setUsername(rs.getString("sender"));
				message.setReceiver(rs.getString("receiver"));
				message.setMessage(rs.getString("message"));

				messagesList.add(message);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return messagesList;
	}
	
	public static List<Message> getMessagesByUserName(String username)
	{
		List<Message> messagesList=new ArrayList<Message>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from querys where sender='"+username+"'");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				Message message=new Message();
				
				message.setId(rs.getInt("id"));
				message.setUsername(rs.getString("sender"));
				message.setReceiver(rs.getString("receiver"));
				message.setMessage(rs.getString("message"));

				messagesList.add(message);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return messagesList;
	}
	
	public static int addReport(Report report)
	{
		int result=0; 

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("insert into report values(?,?,?)");
		
			ps.setInt(1,0);
			ps.setString(2,report.getPatientName());
			ps.setString(3,report.getReportedBy());
			
			result=ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static List<Report> getReportings()
	{
		List<Report> reportsList=new ArrayList<Report>();

		try {

			PreparedStatement ps=MyConnection.getConnection().prepareStatement("select * from report");

			ResultSet rs=ps.executeQuery();

			while (rs.next()) {

				Report report=new Report();
				
				report.setId(rs.getInt("id"));
				report.setPatientName(rs.getString("patientname"));
				report.setReportedBy(rs.getString("reportedby"));

				reportsList.add(report);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return reportsList;
	}
}
