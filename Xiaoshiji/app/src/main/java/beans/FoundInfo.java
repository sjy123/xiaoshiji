package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by db on 6/12/15.
 */
@AVClassName(FoundInfo.FOUNDINFO_CLASS)
public class FoundInfo extends AVObject {

    public static final String FOUNDINFO_CLASS="foundinfo";

    public String FOUNDNAME="foundname";
    public String FOUNDPLACE="foundplace";
    public String FOUNDDESCRIPTION="founddescription";
    public String FOUNDCONTACT="foundcontact";
    public String FOUNDATTACH="foundattach";
    public String FOUNDDATE="founddate";

    public String getFoundAttach() {
        return this.getString(FOUNDATTACH);
    }

    public void setFoundAttach(String lostAttach) {
        this.put(FOUNDATTACH,lostAttach);
    }

    public String getFoundContact() {
        return this.getString(FOUNDCONTACT);
    }

    public void setFoundContact(String lostContact) {
        this.put(FOUNDCONTACT,lostContact);
    }

    public String getFoundDescription() {
        return this.getString(FOUNDDESCRIPTION);
    }

    public void setFoundDescription(String lostDescription) {
        this.put(FOUNDDESCRIPTION,lostDescription);
    }

    public String getFoundPlace() {
        return this.getString(FOUNDPLACE);
    }

    public void setFoundPlace(String lostPlace) {
        this.put(FOUNDPLACE,lostPlace);
    }

    public String getFoundName() {
        return this.getString(FOUNDNAME);
    }

    public void setFoundName(String lostname) {
        this.put(FOUNDNAME,lostname);
    }

    public void setFoundDate(String lostdate){
        this.put(FOUNDDATE,lostdate);
    }

    public String getFoundDate(){
        return this.getString(FOUNDDATE);
    }

}
