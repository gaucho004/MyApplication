package activities.doctorswall.model;

import com.parse.ParseFile;

import java.util.Date;

/**
 * Created by f8 on 08/09/2015.
 */
public class ChatPeople implements Comparable<ChatPeople> {

    private String TABLE_PEOPLE = "people_table";
    int DATABASE_VERSION = 1;
    private String ID = "id";
    private String PERSON_NAME = "person_name";
    private String PERSON_EMAIL = "person_email";
    private String PERSON_DEVICE_ID = "person_device_id";

    private String PERSON_CHAT_TO_FROM = "to_or_from";
    private String PERSON_DATETIME = "datetime";
    private String type;
    private ParseFile imageFile;
    private String imageUrl;
    private String datashared;


    private Date datetime;
    private String sender;
    private String mall_chat_id;
    private String ipayu_user_id;
    private String PERSON_CHAT_MESSAGE = "person_chat_message";
    private boolean isgroupchat=false;

    public void setDatashared(String datashared) {
        this.datashared = datashared;
    }

    public String getDatashared() {
        return datashared;
    }

    public void setIsgroupchat(boolean isgroupchat) {
        this.isgroupchat = isgroupchat;
    }

    public boolean isgroupchat() {
        return isgroupchat;
    }

    public void setIpayu_user_id(String ipayu_user_id) {
        this.ipayu_user_id = ipayu_user_id;
    }

    public void setMall_chat_id(String mall_chat_id) {
        this.mall_chat_id = mall_chat_id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getIpayu_user_id() {
        return ipayu_user_id;
    }

    public String getMall_chat_id() {
        return mall_chat_id;
    }

    public String getSender() {
        return sender;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageFile(ParseFile imageFile) {
        this.imageFile = imageFile;
    }

    public ParseFile getImageFile() {
        return imageFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(ChatPeople o) {
        return getDatetime().compareTo(o.getDatetime());
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


    public Date getDatetime() {
        return datetime;
    }

    public void setPERSON_DATETIME(String PERSON_DATETIME) {
        this.PERSON_DATETIME = PERSON_DATETIME;
    }

    public String getPERSON_DATETIME() {
        return PERSON_DATETIME;
    }

    public String getPERSON_CHAT_TO_FROM() {
        return PERSON_CHAT_TO_FROM;
    }

    public void setPERSON_CHAT_TO_FROM(String pERSON_CHAT_TO_FROM) {
        PERSON_CHAT_TO_FROM = pERSON_CHAT_TO_FROM;
    }

    public String getPERSON_CHAT_MESSAGE() {
        return PERSON_CHAT_MESSAGE;
    }

    public void setPERSON_CHAT_MESSAGE(String pERSON_CHAT_MESSAGE) {
        PERSON_CHAT_MESSAGE = pERSON_CHAT_MESSAGE;
    }

    public String getTABLE_PEOPLE() {
        return TABLE_PEOPLE;
    }

    public void setTABLE_PEOPLE(String tABLE_PEOPLE) {
        TABLE_PEOPLE = tABLE_PEOPLE;
    }

    public int getDATABASE_VERSION() {
        return DATABASE_VERSION;
    }

    public void setDATABASE_VERSION(int dATABASE_VERSION) {
        DATABASE_VERSION = dATABASE_VERSION;
    }

    public String getPERSON_NAME() {
        return PERSON_NAME;
    }

    public void setPERSON_NAME(String pERSON_NAME) {
        PERSON_NAME = pERSON_NAME;
    }

    public String getPERSON_EMAIL() {
        return PERSON_EMAIL;
    }

    public void setPERSON_EMAIL(String pERSON_EMAIL) {
        PERSON_EMAIL = pERSON_EMAIL;
    }

    public String getPERSON_DEVICE_ID() {
        return PERSON_DEVICE_ID;
    }

    public void setPERSON_DEVICE_ID(String pERSON_DEVICE_ID) {
        PERSON_DEVICE_ID = pERSON_DEVICE_ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTableName() {
        return TABLE_PEOPLE;
    }

    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public String getID() {
        return ID;
    }

    public String getFilename() {
        return PERSON_NAME;
    }

    public String getDatabaseCreateQuery() {
        final String DATABASE_CREATE = "create table IF NOT EXISTS "
                + TABLE_PEOPLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + PERSON_NAME + " TEXT NOT NULL, " + PERSON_CHAT_MESSAGE
                + " TEXT NOT NULL, " + PERSON_EMAIL + " TEXT NOT NULL, "
                + PERSON_DEVICE_ID + " TEXT NOT NULL, " + PERSON_CHAT_TO_FROM
                + " TEXT NOT NULL )";
        System.out.println(DATABASE_CREATE);

        return DATABASE_CREATE;
    }
}

