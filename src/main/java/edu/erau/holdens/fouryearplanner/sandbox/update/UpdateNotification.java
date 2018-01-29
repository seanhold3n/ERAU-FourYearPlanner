package edu.erau.holdens.fouryearplanner.sandbox.update;

import java.util.Calendar;

import com.google.gson.Gson;

public class UpdateNotification implements Comparable<UpdateNotification> {

	/** The version number for the update */
	private String version_number;
	/** The date that the update was released, in yyyy-mm-dd format */
	private String date;
	/** The message about the update */
	private String message;
	
	
	/** Converts a properly-formatted JSON string to a LogEntry object
	 * @param s The JSON String to parse
	 * @return A LogEntry object matching the provided JSON string
	 */
	public static UpdateNotification parseFromJSON(String s){
		return new Gson().fromJson(s, UpdateNotification.class);
	}
	
	
	/** Creates a new UpdateNotification object
	 * @param version_number The version number for the update
	 * @param date The date that the update was released, in yyyy-mm-dd format
	 * @param message The message about the update
	 */
	public UpdateNotification(String version_number, String date, String message) {
		super();
		this.version_number = version_number;
		this.date = date;
		this.message = message;
	}
	

	/** 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	// TODO ADD DOC
	@Override
	public int compareTo(UpdateNotification o) {
		return this.date.compareTo(o.date) * -1;	// -1 for descending
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UpdateNotification){
			UpdateNotification obj2 = (UpdateNotification) obj;
			return (this.version_number.equals(obj2.version_number)
					&& this.date.equals(obj2.date)
					&& this.message.equals(obj2.message));
		}
		else{
			return false;
		}
	};


	/**
	 * @return The date as a Calendar object
	 */
	public Calendar getDate(){
		String[] dateParts = date.split("-");
		Calendar date = new Calendar.Builder()
		.setDate(	Integer.parseInt(dateParts[0]),
					Integer.parseInt(dateParts[1]),
					Integer.parseInt(dateParts[2]))
		.build();
		return date;
	}
	
	
	/**
	 * @return The date as a String in yyyy-mm-dd format
	 */
	public String getDateString(){
		return date;
	}
	
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	
	/**
	 * @return the version_number
	 */
	public String getVersionNumber() {
		return version_number;
	}


	public String toJsonString(){
		return new Gson().toJson(this);
	}

	
	public String toString(){
		return toJsonString();
	}
	
	
}
