package app.Utilities;

import app.Model.Examiner;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by husam on 7/7/17.
 */
public class SessionManager {

    private DBHandler dbHandler = null;
    private String  KEY_ID = "id",
                    KEY_USERNAME = "username",
                    KEY_PASSWORD = "password",
                    KEY_FIRSTNAME = "firstname",
                    KEY_LASTNAME = "lastname",
                    KEY_TITLE = "title";

    private Preferences pref = null;

    public SessionManager(DBHandler dbHandler) {
        this.dbHandler = dbHandler;

        // Define a node to store the preferences data.
        pref = Preferences.userRoot().node(getClass().getName());

    }

    public boolean login(String username, String password){
        try{
            Examiner examiner = dbHandler.readExaminer(username, password);

            if(examiner == null)
                return false;

            pref.putInt(KEY_ID, examiner.getId());
            pref.put(KEY_USERNAME, examiner.getUsername());
            pref.put(KEY_PASSWORD, examiner.getPassword());
            pref.put(KEY_FIRSTNAME, examiner.getFirstName());
            pref.put(KEY_LASTNAME, examiner.getLastName());
            pref.putInt(KEY_TITLE, examiner.getTitle());
            System.out.println("Login successful");
            return true;
        }catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }

    }

    public void logout(){
        try{
            pref.clear();
            System.out.println("Session cleared, logged out.");
        }catch (BackingStoreException e){
            System.out.println(e.getMessage());
        }
    }

    public Examiner getUserDetails(){
        Examiner examiner = null;

        int id = pref.getInt(KEY_ID, -1),
            title = pref.getInt(KEY_TITLE, -1);

        String username = pref.get(KEY_USERNAME, ""),
                password = pref.get(KEY_PASSWORD, ""),
                firstname = pref.get(KEY_FIRSTNAME, ""),
                lastname = pref.get(KEY_LASTNAME, "");

        examiner = new Examiner(id, title, firstname, lastname, username, password);
        return examiner;
    }

        /**
         * Print Keys in the preferences node.
         * @param pref Preference node.
         */
        public void printKeys(Preferences pref) {
            System.out.println("PreferencesExample.printKeys");
            try {
                String[] keys = pref.keys();
                for (String key : keys) {
                    System.out.println("Key = " + key);
                }
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
            System.out.println("============================");
        }

}
