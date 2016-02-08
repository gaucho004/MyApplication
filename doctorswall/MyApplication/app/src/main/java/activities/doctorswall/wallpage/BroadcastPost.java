package activities.doctorswall.wallpage;

import java.util.Date;

/**
 * Created by Gaucho on 15/12/2015.
 */
public class BroadcastPost implements Comparable<BroadcastPost> {

    private String broadcastid;

    private String urlProfileImage;
    private String mallName;
    private String datePosted;

    private String urlPostImage;
    private String currentRate;
    private String numComments;

    private String postHeadTasg;
    private String postSummary;
    private Date datetime;
    private boolean hasimage;

    public void setHasimage(boolean hasimage) {
        this.hasimage = hasimage;
    }

    public boolean isHasimage() {
        return hasimage;
    }

    public void setBroadcastid(String broadcastid) {
        this.broadcastid = broadcastid;
    }

    public String getBroadcastid() {
        return broadcastid;
    }

    private boolean haspostimage;

    public void setHaspostimage(boolean haspostimage) {
        this.haspostimage = haspostimage;
    }

    public boolean haspostImage(){
        return this.haspostimage;
    }

    public String getUrlProfileImage() {
        return urlProfileImage;
    }

    public void setUrlProfileImage(String urlProfileImage) {
        this.urlProfileImage = urlProfileImage;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getUrlPostImage() {
        return urlPostImage;
    }

    public void setUrlPostImage(String urlPostImage) {
        this.urlPostImage = urlPostImage;
    }

    public String getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(String currentRate) {
        this.currentRate = currentRate;
    }

    public String getNumComments() {
        return numComments;
    }

    public void setNumComments(String numComments) {
        this.numComments = numComments;
    }

    public String getPostHeadTasg() {
        return postHeadTasg;
    }

    public void setPostHeadTasg(String postHeadTasg) {
        this.postHeadTasg = postHeadTasg;
    }

    public String getPostSummary() {
        return postSummary;
    }

    public void setPostSummary(String postSummary) {
        this.postSummary = postSummary;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getDatetime() {
        return datetime;
    }

    @Override
    public int compareTo(BroadcastPost o) {
        return getDatetime().compareTo(o.getDatetime());
    }
}
