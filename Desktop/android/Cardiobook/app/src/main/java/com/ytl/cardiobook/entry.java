package com.ytl.cardiobook;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class entry {
	private String Date;
	private String Time;
	private String systolicPressure;
	private String diastolicPressure;
	private String heartRate;
	private String comment;
	
	public entry() {
		
		this.Date = "";
		this.Time = "";
		this.systolicPressure = "";
		this.diastolicPressure = "";
		this.heartRate = "";
		this.comment = "comment";

	}
	
	public void setDate(String date) {
		Date = date;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setTime(String time) {
		Time = time;
	}
	
	public void setSystolicPressure(int systolic) {
		if (systolic > 140 || systolic < 90){
			this.systolicPressure = "*" + systolic + "*";
		}else{
			this.systolicPressure = Integer.toString(systolic);
		}
		
	}
	
	public void setDiastolicPressure(int diastolic) {
		if (diastolic > 90 || diastolic < 60){
			this.diastolicPressure = "*" + diastolic + "*";
		}else{
			this.diastolicPressure = Integer.toString(diastolic);
		}
	}
	
	public void setHeartRate(int rate) {
		if (rate > 100 || rate < 60){ //60-100BPM as google says
			this.heartRate = "*" + rate + "*";
		}else{
			this.heartRate = Integer.toString(rate);
		}
	}
	
	public String getComment() {
		return comment;
	}
	
	@Override
	public String toString(){
		return "Date(yyyy-mm-dd): " + Date + "\n"
				+ "Time(24hr): " + Time + "\n"
				+ "Systolic Pressure: " + systolicPressure + "mm Hg"+ "\n"
				+ "Diastolic Pressure: " + diastolicPressure + "mm Hg"+ "\n"
				+ "Heart Rate: " + heartRate + "BPM"+ "\n"
				+ comment;
	} //need toString method, other wise prints com.ytl.cardiobook@entryGibberish
}
