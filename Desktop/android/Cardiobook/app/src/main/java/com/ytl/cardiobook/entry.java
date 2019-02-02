package com.ytl.cardiobook;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class entry {
	private Date Date;
	private Time Time;
	private int systolicPressure;
	private int diastolicPressure;
	private int heartRate;
	private String comment;
	
	public entry() {

		this.comment = "comment";

	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
	
	@Override
	public String toString(){
		return comment;
	} //need toString method, other wise prints com.ytl.cardiobook@entryGibberish
}
