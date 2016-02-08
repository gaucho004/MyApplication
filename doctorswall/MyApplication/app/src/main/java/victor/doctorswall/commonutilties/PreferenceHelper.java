package victor.doctorswall.commonutilties;

import android.content.Context;
import android.content.SharedPreferences;

import com.parse.ParseFile;

/**
 * Created by Cowboy on 1/9/2016.
 */
public class PreferenceHelper {
    private SharedPreferences app_prefs;
    private Context context;


    private final String USER_ID = "user_id";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String DEVICE_TOKEN = "device_token";
    private final String SESSION_TOKEN = "session_token";
    private final String REQUEST_ID = "request_id";
    private final String REQUEST_TIME = "request_time";
    private final String REQUEST_LATITUDE = "request_latitude";
    private final String REQUEST_LONGITUDE = "request_longitude";
    private final String LOGIN_BY = "login_by";
    private final String SOCIAL_ID = "social_id";
    private final String REFERRAL_CODE = "referral_code";

    private final String USER_NAME = "username";
    private final String SPECIALTIES = "specialty";
    private final String URLPHOTO = "urlphoto";
    private final String URLPHOTOWALL = "wallurlphoto";



    //private final ParseFile URL_PROFILE_IMAGE=null;

    public void putPhoneNumber(String pnum) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("pnum", pnum);
        edit.commit();
    }

    public String getPhonenumber() {
        return app_prefs.getString("pnum", "");
    }


    public void putAddress(String address) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("adds", address);
        edit.commit();
    }

    public String getAddress() {
        return app_prefs.getString("adds", "");
    }


    public void putFname(String fname) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("fname", fname);
        edit.commit();
    }

    public String getFname() {
        return app_prefs.getString("fname", "");
    }

    public void putLname(String lname) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString("lname", lname);
        edit.commit();
    }

    public String getLname() {
        return app_prefs.getString("lname", "");
    }


    public void putEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }



    public String getEmail() {
        return app_prefs.getString(EMAIL, "");
    }

   /* public void putProfilePic(ParseFile url){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(URL_PROFILE_IMAGE, url);
        edit.commit();
    }*/

    /*public  String getURL_PROFILE_IMAGE(){
        return app_prefs.getString(URL_PROFILE_IMAGE, null);
    }*/


    public void putSpecialties(String specialties){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(SPECIALTIES, specialties);
        edit.commit();
    }

    public void putURLImagePhoto(String urlphoto){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(URLPHOTO, urlphoto);
        edit.commit();
    }

    public String getURLPhoto(){
        return app_prefs.getString(URLPHOTO, "");
    }

    public String getSpecialties(){
        return app_prefs.getString(SPECIALTIES, "");
    }

    public void putUserName(String username){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(USER_NAME, username);
        edit.commit();
    }

    public  String getUSER_NAME(){
        return app_prefs.getString(USER_NAME, null);
    }


    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("doctor_prefs",
                Context.MODE_PRIVATE);
        this.context = context;
    }


    public void putURLWallPic(String urlwallpic){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(URLPHOTOWALL, urlwallpic);
        edit.commit();
    }

    public String getWallPhoto(){
        return app_prefs.getString(URLPHOTOWALL,"");
    }



    public void putUserId(String userId) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(USER_ID, userId);
        edit.commit();
    }


    public String getUserId() {
        return app_prefs.getString(USER_ID, null);

    }

    public void Logout() {

        // new DBHelper(context).deleteUser();
        putUserId(null);
   //     putProfilePic(null);
        putUserName(null);
        putEmail(null);
        putURLImagePhoto("");
        putSpecialties("");


    }
}
