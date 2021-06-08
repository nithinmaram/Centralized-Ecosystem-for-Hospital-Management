package com.voidmain.pm.form;

public class DischargeForm {

	private int dischargeId;
	private String patientName;
	private String dischargeDate;
	private String dischargeSummary;
	private String hospitalName;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	public String getDischargeSummary() {
		return dischargeSummary;
	}
	public void setDischargeSummary(String dischargeSummary) {
		this.dischargeSummary = dischargeSummary;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public int getDischargeId() {
		return dischargeId;
	}
	public void setDischargeId(int dischargeId) {
		this.dischargeId = dischargeId;
	}
}
