package activities.doctorswall.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import centennial.ipayu.shoppinglist.ShoppingList;

/**
 * Created by Lenovo on 4/1/2016.
 */
public class ContactsDB {

    /******** if debug is set true then it will show all Logcat message *******/
    public static final boolean DEBUG = true;
    /******************** Logcat TAG ************/
    public static final String LOG_TAG = "DBAdapter";

    /******************** Database Name ************/
    public static final String DATABASE_NAME = "ContactsDB";

    /** Table names */
    public static final String CONTACTS_TABLE = "tbl_contactlist";

    /******************** Table Fields ************/
    public static final String KEY_ID = "_id";

    public static final String KEY_FNAME = "fname";

    public static final String KEY_LNAME = "lname";

    public static final String KEY_IPAYUID = "ipayuid";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_THIS_IPAYUID = "thisipayu";

    public static final String KEY_IS_GROUPCHAT = "isgroupchat";




    /****************** Table Fields GroupChat *************************/
    public static final String KEY_ID_GROUPCHAT = "_gcid";

    public static final String KEY_CHAT_NAME = "chatname";

    /**** Database Version (Increase one if want to also upgrade your database) ***/
    public static final int DATABASE_VERSION = 1;// started at 1

    /******************** Used to open database in syncronized way ************/
    private static DataBaseHelper DBHelper = null;

    /** Create table shopping list */
    private static final String CONTACT_LIST_TABLE_CREATE = "create table "+CONTACTS_TABLE+"( "+KEY_ID+" integer primary key autoincrement,"+KEY_FNAME+" text not null,"+KEY_LNAME+" text not null," +KEY_IPAYUID+" text not null," + KEY_USERNAME + " text not null, " + KEY_THIS_IPAYUID + " integer not null, " + KEY_IS_GROUPCHAT + " text not null);";

    /******* Set all table with comma seperated like USER_TABLE,ABC_TABLE *******/
    private static final String[ ] ALL_TABLES = { CONTACTS_TABLE };

    protected ContactsDB() {

    }

    /********** Initialize database *********/
    public static void init(Context context) {
        if (DBHelper == null) {
            if (DEBUG)
                Log.i("DBAdapter", context.toString());
            DBHelper = new DataBaseHelper(context);
        }
    }
    /***** Open database for insert,update,delete in syncronized manner ****/
    private static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }


    /*********** Escape string for single quotes (Insert,Update) ********/
    private static String sqlEscapeString(String aString) {

        String aReturn = "";

        if (null != aString) {
            //aReturn = aString.replace(", );
            aReturn = DatabaseUtils.sqlEscapeString(aString);
            // Remove the enclosing single quotes ...
            aReturn = aReturn.substring(1, aReturn.length() - 1);
        }

        return aReturn;

    }


    //  ******** User data functons for table contacts ********

    public static long addcontacts(Contacts new_contact, int myipayuid) throws SQLException {

        // Open database for Read / Write

        final SQLiteDatabase db = open();
        String isgroup;
        String fname  = sqlEscapeString(new_contact.getFname());
        String lname = sqlEscapeString(new_contact.getLname());
        String ipayuid = sqlEscapeString(new_contact.getIpayuid());
        String uname = sqlEscapeString(new_contact.getUsername_code());
        if(new_contact.isGroupChat()){
            isgroup = sqlEscapeString("yes");
        }else{
            isgroup = sqlEscapeString("no");

        }
        ContentValues cVal = new ContentValues();
        cVal.put(KEY_FNAME, fname);
        cVal.put(KEY_LNAME, lname);
        cVal.put(KEY_IPAYUID, ipayuid);
        cVal.put(KEY_USERNAME, uname);
        cVal.put(KEY_THIS_IPAYUID, myipayuid);
        cVal.put(KEY_IS_GROUPCHAT, isgroup );
        Log.d("action", "inserted " + isgroup);

        // Insert user values in database
        long result = db.insert(CONTACTS_TABLE, null, cVal);
        db.close(); // Closing database connection
        return result; //check if inserting was success = -1 < 0
    }


    //get all

    // Getting All shopping list data
    public static List<Contacts> getAllContacts(int ipayuid) throws SQLException {

        List<Contacts> contactList = new ArrayList<Contacts>();


        // Select All Query
        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE;




        // Open database for Read / Write
        final SQLiteDatabase db = open();


        Cursor cursor = db.query(CONTACTS_TABLE, new String[]{KEY_ID,
                        KEY_FNAME, KEY_LNAME, KEY_IPAYUID, KEY_USERNAME, KEY_THIS_IPAYUID, KEY_IS_GROUPCHAT}, KEY_THIS_IPAYUID + "=?",
                new String[]{String.valueOf(ipayuid)}, null, null, null, null);



        // Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contacts data = new Contacts();

                data.setId(cursor.getInt(0) + "");
                data.setFname(cursor.getString(1));
                data.setLname(cursor.getString(2));
                data.setIpayuid(cursor.getString(3));
                data.setUsername_code(cursor.getString(4));

                if(cursor.getString(6).equals("yes")) {
                    data.setIsGroupChat(true);
                }else{
                    data.setIsGroupChat(false);

                }



                //Log.d("action", )

                // Adding contact to list
                contactList.add(data);
            } while (cursor.moveToNext());
        }

        // return user list
        return contactList;
    }




    /********** UnEscape string for single quotes (show data) ************/
    private static String sqlUnEscapeString(String aString) {

        if (aString == null)
            return null;

        StringBuilder sb = new StringBuilder(aString);

        sb = sb.deleteCharAt(0);
        sb = sb.deleteCharAt(sb.length() - 1);

        return sb.toString().replaceAll("''", "'");
    }





    /********** Main Database creation INNER class ********/
    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (DEBUG)
                // Log.i(LOG_TAG, "new create");
                try {

                    //create two tablees
                    db.execSQL(CONTACT_LIST_TABLE_CREATE);



                } catch (Exception exception) {
                    if (DEBUG)
                        Log.i(LOG_TAG, "Exception onCreate() exception");
                }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (DEBUG)
                Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
                        + "to" + newVersion + "...");

            for (String table : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            onCreate(db);
        }

    } // Inner class closed





    // Getting single item
    public static Contacts getThisItem(int id, int ipayu_id) throws SQLException {
        Contacts thisItem = null;
        // Open database for Read / Write
        final SQLiteDatabase db = open();

        Cursor cursor = db.query(CONTACTS_TABLE, new String[] { KEY_ID,
                        KEY_FNAME, KEY_LNAME, KEY_IPAYUID, KEY_USERNAME , KEY_IS_GROUPCHAT}, KEY_IPAYUID + " = ? AND " + KEY_THIS_IPAYUID + " = ?",
                new String[] { String.valueOf(id) , String.valueOf(ipayu_id) }, null, null, null, null);


        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();
            //                               int id,              String name, int status,    int quantity, String price, String datetime, int shoppinglistLink
            thisItem = new Contacts(cursor.getInt(0) + "", cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        // return user data
        return thisItem;

    }

    public static boolean isHere(String usercode, int ipayuid) throws SQLException{

        // Open database for Read / Write
        final SQLiteDatabase db = open();

        Cursor cursor = db.query(CONTACTS_TABLE, new String[] { KEY_ID,
                        KEY_FNAME, KEY_LNAME, KEY_IPAYUID, KEY_USERNAME , KEY_IS_GROUPCHAT}, KEY_USERNAME + " = ? AND " + KEY_THIS_IPAYUID + " = ?",
                new String[] { usercode, String.valueOf(ipayuid) }, null, null, null, null);

        if(cursor.moveToFirst()) {
            return true;
        }

        return false;
    }


    public static Contacts getThisItem(String id, int ipayu_id) throws  SQLException{
        Contacts thisItem = null;
        // Open database for Read / Write
        final SQLiteDatabase db = open();
        Cursor cursor = db.query(CONTACTS_TABLE, new String[]{KEY_ID,
                        KEY_FNAME, KEY_LNAME, KEY_IPAYUID, KEY_USERNAME , KEY_THIS_IPAYUID, KEY_IS_GROUPCHAT}, KEY_FNAME + " = ? AND " + KEY_THIS_IPAYUID + " = ?",
                new String[]{id, String.valueOf(ipayu_id)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            //  cursor.moveToFirst();
            //                               int id,              String name, int status,    int quantity, String price, String datetime, int shoppinglistLink
            thisItem = new Contacts(cursor.getInt(0) + "", cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        // return user data
        return thisItem;
    }

    // Deleting single contact in list
    public static void deleteItem(Contacts item) throws SQLException {
        final SQLiteDatabase db = open();
        db.delete(CONTACTS_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    //delete group name
    public static void deleteItem(Contacts item, boolean isgroupname) throws SQLException {

        final SQLiteDatabase db = open();
        db.delete(CONTACTS_TABLE, KEY_FNAME + " = ?",
                new String[] { item.getFname() });
        db.close();

    }

}
