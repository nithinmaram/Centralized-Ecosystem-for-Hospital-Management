package com.voidmain.pm.form;

public class SurgaryForm {

	private int surgaryId;
	private String surgaryName;
	private String patientName;
	private String date;
	private String hospitalName;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public int getSurgaryId() {
		return surgaryId;
	}
	public void setSurgaryId(int surgaryId) {
		this.surgaryId = surgaryId;
	}
	public String getSurgaryName() {
		return surgaryName;
	}
	public void setSurgaryName(String surgaryName) {
		this.surgaryName = surgaryName;
	}
}
