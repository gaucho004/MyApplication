package activities.doctorswall.model;

/**
 * Created by f8 on 08/09/2015.
 */
public class PeopleObject implements Comparable<PeopleObject> {

    String personName, regId, fname, lname, ipayuid;
    boolean isGroupChat;

    long lastseen;

    public void setIsGroupChat(boolean isGroupChat) {
        this.isGroupChat = isGroupChat;
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public void setLastseen(long lastseen) {
        this.lastseen = lastseen;
    }

    public long getLastseen() {
        return lastseen;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setIpayuid(String ipayuid) {
        this.ipayuid = ipayuid;
    }

    public String getIpayuid() {
        return ipayuid;
    }

    @Override
    public int compareTo(PeopleObject another) {
        PeopleObject a = this;
        PeopleObject b = another;

        int res =  a.getLname().compareToIgnoreCase(b.getLname());
        if (res != 0)
            return res;
        return a.getLname().compareToIgnoreCase(b.getLname());

    }
}
