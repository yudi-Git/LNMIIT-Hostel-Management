package lnmiit.hostel.management;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManagement {

	private SharedPreferences prefs;
	static public String ip = "10.0.2.2";

	public SessionManagement(Context cntx) {
		// TODO Auto-generated constructor stub
		prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
		
	}

	public void setusertype(String usertype) {
		prefs.edit().putString("usertype", usertype).commit();
		// prefsCommit();
	}

	public String getusertype() {
		String usertype = prefs.getString("usertype", "");
		return usertype;
	}

	public void setuserid(int userid) {
		prefs.edit().putInt("userid", userid).commit();
		// prefsCommit();
	}

	public int getuserid() {
		int userid = prefs.getInt("userid", 0);
		return userid;
	}

}