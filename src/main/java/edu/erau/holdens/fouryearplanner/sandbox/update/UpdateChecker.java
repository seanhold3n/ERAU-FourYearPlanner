package edu.erau.holdens.fouryearplanner.sandbox.update;

import java.io.IOException;

import edu.erau.holdens.fouryearplanner.Constants;
import edu.erau.holdens.fouryearplanner.io.Utils;


public class UpdateChecker {
	
	/**
	 * @return The list of updates since this version, 
	 */
	public static UpdateList getUpdates(){
		
		String jsonString;

		try{
			// Fetch the list of updates from the server
			System.out.println("[UpdateChecker] Connecting to update site...");
			jsonString = Utils.getURL(Constants.UPDATE_ENDPOINT);
		} catch (IOException e) {
			System.err.println("[UpdateChecker] Error connecting to update site.");
			return null;
		}

		UpdateList updates = UpdateList.parseFromJSON(jsonString);
		updates = updates.getUpdatesAfter(Constants.PROGRAM_DATE);


		System.out.println("[UpdateChecker] " 
		+ ((updates == null) ? "All up-to-date!" : "Update available!")
		+ "  Yay!");
		
		return updates;
	}

}
