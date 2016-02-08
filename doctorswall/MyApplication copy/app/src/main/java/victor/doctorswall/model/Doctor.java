package victor.doctorswall.model;

/**
 * Created by Cowboy on 1/2/2016.
 */
public class Doctor {

    private String firstname;
    private String lastname;
    private String _address; //Mr. , Ms. , Dr. ,
    private String titles;
    private String address;
    private String contacts;
    private String professionalStatement;
    private String profilepic;
    private String email;
    private String alternativeEmail;
    private String wallpaperpic;

    public void setWallpaperpic(String wallpaperpic) {
        this.wallpaperpic = wallpaperpic;
    }

    public String getWallpaperpic() {
        return wallpaperpic;
    }

    public void setAlternativeEmail(String alternativeEmail) {
        this.alternativeEmail = alternativeEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternativeEmail() {
        return alternativeEmail;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getProfessionalStatement() {
        return professionalStatement;
    }

    public void setProfessionalStatement(String professionalStatement) {
        this.professionalStatement = professionalStatement;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
