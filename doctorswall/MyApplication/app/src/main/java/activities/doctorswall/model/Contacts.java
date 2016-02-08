package activities.doctorswall.model;

/**
 * Created by Lenovo on 4/1/2016.
 */
public class Contacts implements Comparable<Contacts> {

    private String fname;
    private String lname;
    private String ipayuid;
    private String id;
    private String username_code;
    private boolean isSelected = false;
    private boolean isGroupChat = false;


    public Contacts(){

    }

    public void setIsGroupChat(boolean isGroupChat) {
        this.isGroupChat = isGroupChat;
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Contacts(String id, String fname, String lname, String ipayuid, String username_code){
        this.id = id;
        this.ipayuid = ipayuid;
        this.fname = fname;
        this.lname = lname;
        this.username_code = username_code;
    }

    public void setUsername_code(String username_code) {
        this.username_code = username_code;
    }

    public String getUsername_code() {
        return username_code;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getIpayuid() {
        return ipayuid;
    }

    public void setIpayuid(String ipayuid) {
        this.ipayuid = ipayuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Contacts another) {

        Contacts a = this;
        Contacts b = another;

        int res =  a.getLname().compareToIgnoreCase(b.getLname());
        if (res != 0)
            return res;
        return a.getLname().compareToIgnoreCase(b.getLname());

    }
}

