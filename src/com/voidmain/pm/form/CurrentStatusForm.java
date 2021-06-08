package com.voidmain.pm.form;

public class CurrentStatusForm {

	private String patientName;
	private String currentStatus;
	private String currentStatusdate;
	private String hospitalName;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getCurrentStatusdate() {
		return currentStatusdate;
	}
	public void setCurrentStatusdate(String currentStatusdate) {
		this.currentStatusdate = currentStatusdate;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
}
