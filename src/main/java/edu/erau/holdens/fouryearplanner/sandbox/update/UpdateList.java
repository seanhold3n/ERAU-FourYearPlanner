package edu.erau.holdens.fouryearplanner.sandbox.update;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class UpdateList extends ArrayList<UpdateNotification> implements List<UpdateNotification> {

	private static final long serialVersionUID = -2956794431607644933L;
	
	/** Converts a properly-formatted JSON string to a LogEntry object
	 * @param s The JSON String to parse
	 * @return A LogEntry object matching the provided JSON string
	 */
	public static UpdateList parseFromJSON(String s){
		return new Gson().fromJson(s, UpdateList.class);
	}
	
	public UpdateList getUpdatesAfter(String date){
		
		sort();
		
		UpdateList recentUpdates = null;
		
		for (UpdateNotification u : this){
			if (u.getDateString().compareTo(date) > 0){
				// Update!
				// Lazy creation - yay!
				if (recentUpdates == null){
					recentUpdates = new UpdateList();
				}
				recentUpdates.add(u);
			}
			else {
				// If the list is sorted by date, and this update is older than the string, all subsequent strings will be also
				break;
			}
		}
		
		return recentUpdates;
		
	}
	
	/** Sorts the list according to the compareTo(...) method in UpdateNotification.
	 * @see UpdateNotification
	 */
	public void sort(){
		this.sort(null);
	}
	
	public String toJsonString(){
		return new Gson().toJson(this);
	}

}
