package edu.erau.holdens.fouryearplanner.sandbox.update;

public class UpdatePlayground {

	public static void main(String[] args) {
		UpdateNotification notif1, notif2;
		
		notif1 = new UpdateNotification("1.0-beta", "2015-11-01", "Initial release.");
		String jsonStr = notif1.toJsonString();
		System.out.println(jsonStr);
		
		notif2 = UpdateNotification.parseFromJSON(jsonStr);
		String jsonStr2 = notif2.toJsonString();
		System.out.println(jsonStr2);
		
		System.out.println(notif1.equals(notif2) ? "Equal" : "Not equal");
		
		
		UpdateList updateList = new UpdateList();
		updateList.add(notif1);
		updateList.add(new UpdateNotification("1.1-beta", "2015-11-05", "This version adds an update checker."));
		
		updateList.sort();
		System.out.println(updateList.toJsonString());
		System.out.println(updateList.getUpdatesAfter("2015-10-30"));
		
		System.out.println(UpdateChecker.getUpdates());
		
	}
	
}
