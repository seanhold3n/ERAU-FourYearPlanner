package edu.erau.holdens.fouryearplanner.test.sandbox;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import edu.erau.holdens.fouryearplanner.sandbox.update.UpdateNotification;

public class UpdateNotificationTest {
	
	private UpdateNotification notif;
	
	private static final String ESCAPED_JSON_STR = 
			"{\"version_number\":\"1.0-beta\",\"date\":\"2015-11-01\",\"message\":\"Initial release.\"}";
	private static final UpdateNotification NOTIF_FROM_JSON = 
			new UpdateNotification("1.0-beta", "2015-11-01", "Initial release.");

	@Test
	public void testParseFromJSON() {
		notif = UpdateNotification.parseFromJSON(ESCAPED_JSON_STR);
		assertEquals(NOTIF_FROM_JSON, notif);
	}

	@Test
	public void testCompareTo() {
		UpdateNotification notif1, notif2;
		notif1 = NOTIF_FROM_JSON;
		notif2 = new UpdateNotification("1.0-beta", "2015-11-01", "Initial release.");
		
		// Equal
		assertEquals(0, notif1.compareTo(notif2));
		
		// Equal independent of version #
		notif2 = new UpdateNotification("1.1", "2015-11-01", "Initial release.");
		assertEquals(0, notif1.compareTo(notif2));		
		
		// Equal independent of message
		notif2 = new UpdateNotification("1.0-beta", "2015-11-01", "Something else");
		assertEquals(0, notif1.compareTo(notif2));
		
		
		// Less than - newer date
		notif2 = new UpdateNotification("1.0-beta", "2015-11-02", "Initial release.");
		assertTrue(notif2.compareTo(notif1) < 0);
		
		// Greater than - older date
		notif2 = new UpdateNotification("1.0-beta", "2015-10-30", "Initial release.");
		assertTrue(notif2.compareTo(notif1) > 0);
		
		
	}

	@Test
	public void testEquals() {
		UpdateNotification notif1, notif2;
		notif1 = NOTIF_FROM_JSON;
		notif2 = new UpdateNotification("1.0-beta", "2015-11-01", "Initial release.");
		
		assertEquals(notif1, notif2);
		
		// Change Version # only
		notif2 = new UpdateNotification("1.1", "2015-11-01", "Initial release.");
		assertNotEquals(notif1, notif2);
		
		// Change date only
		notif2 = new UpdateNotification("1.0-beta", "2015-11-02", "Initial release.");
		assertNotEquals(notif1, notif2);
		
		// Change message only
		notif2 = new UpdateNotification("1.0-beta", "2015-11-01", "Something else");
		assertNotEquals(notif1, notif2);
		
		// And now for something completely different
		assertNotEquals(notif1,  new Object());
		
	}

	@Test
	public void testGetDates() {
		final String DATESTR = "2015-11-01";
		notif = new UpdateNotification("", DATESTR, "");
		Calendar date = notif.getDate();
		assertEquals(DATESTR, notif.getDateString());
		assertEquals(2015, date.get(Calendar.YEAR));
		assertEquals(11, date.get(Calendar.MONTH));
		assertEquals(01, date.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testGetMessage() {
		final String MSGSTR = "This update has stuff.";
		notif = new UpdateNotification("", "", MSGSTR);
		assertEquals(MSGSTR, notif.getMessage());
	}

	@Test
	public void testGetVersionNumber() {
		final String VERSIONSTR = "1.0-beta";
		notif = new UpdateNotification(VERSIONSTR, "", "");
		assertEquals(VERSIONSTR, notif.getVersionNumber());
	}

	@Test
	public void testToStrings() {
		String str;
		
		str = NOTIF_FROM_JSON.toJsonString();
		assertEquals(ESCAPED_JSON_STR, str);

		str = NOTIF_FROM_JSON.toString();
		assertEquals(ESCAPED_JSON_STR, str);
	}

}
