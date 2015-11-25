package skallaje.cafeteria_app.cs442.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by shara on 11/3/2015.
 */
public class UserSession {

    private SharedPreferences session;

    public UserSession(Context cntx) {
        // TODO Auto-generated constructor stub
        session = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setSession(String id) {
        session.edit().putString("SessionID", id).commit();
    }

    public String getSession() {
        String sID = session.getString("SessionID","");
        return sID;
    }

    public SharedPreferences deleteSession() {
        //String sID = session.getString("SessionID","");
        //session = null;
        session.edit().remove("SessionID").commit();
        return session;
    }
}
