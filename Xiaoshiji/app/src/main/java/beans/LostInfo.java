package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by db on 6/12/15.
 */
@AVClassName(LostInfo.LOSTINFO_CLASS)
public class LostInfo extends AVObject{

    public static final String LOSTINFO_CLASS="lostinfo";

    public String LOSTNAME="lostname";
    public String LOSTPLACE="lostplace";
    public String LOSTDESCRIPTION="lostdescription";
    public String LOSTCONTACT="lostcontact";
    public String LOSTATTACH="lostattach";
    public String LOSTDATE ="lostdate";

    public String getLostAttach() {
        return this.getString(LOSTATTACH);
    }

    public void setLostAttach(String lostAttach) {
        this.put(LOSTATTACH,lostAttach);
    }

    public String getLostContact() {
        return this.getString(LOSTCONTACT);
    }

    public void setLostContact(String lostContact) {
        this.put(LOSTCONTACT,lostContact);
    }

    public String getLostDescription() {
        return this.getString(LOSTDESCRIPTION);
    }

    public void setLostDescription(String lostDescription) {
        this.put(LOSTDESCRIPTION,lostDescription);
    }

    public String getLostPlace() {
        return this.getString(LOSTPLACE);
    }

    public void setLostPlace(String lostPlace) {
        this.put(LOSTPLACE,lostPlace);
    }

    public String getLostName() {
        return this.getString(LOSTNAME);
    }

    public void setLostName(String lostname) {
        this.put(LOSTNAME,lostname);
    }

    public void setLostDate(String lostdate){
        this.put(LOSTDATE,lostdate);
    }

    public String getLostDate(){
        return this.getString(LOSTDATE);
    }

}
