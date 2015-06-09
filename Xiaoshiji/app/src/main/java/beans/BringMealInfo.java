package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by db on 6/2/15.
 */
@AVClassName(BringMealInfo.BRINGMEALINFO_CLASS)
public class BringMealInfo extends AVObject{

    public static final String BRINGMEALINFO_CLASS="bminfo";
    private String MEALNAME="mealname";
    private String MEALTYPE="mealtype";
    private String DESTINATION="destination";
    private String PAYTYPE="paytype";
    private String CONTACTTYPE="contacttype";
    private String AVUSER="avuser";

    public AVUser getAVUser() {
        return this.getAVUser(AVUSER);
    }

    public void setAVUser(AVUser avuser) {
        this.put(AVUSER,avuser);
    }

    public String getMealname() {
        return this.getString(MEALNAME);
    }

    public void setMealname(String mealname) {
        this.put(MEALNAME,mealname);
    }

    public String getMealtype() {
        return this.getString(MEALTYPE);
    }

    public void setMealtype(String mealtype) {
        this.put(MEALTYPE,mealtype);
    }

    public String getDestination() {
        return this.getString(DESTINATION);
    }

    public void setDestination(String destination) {
        this.put(DESTINATION,destination);
    }

    public String getPaytype() {
        return this.getString(PAYTYPE);
    }

    public void setPaytype(String paytype) {
        this.put(PAYTYPE,paytype);
    }

    public String getContacttype() {
        return this.getString(CONTACTTYPE);
    }

    public void setContacttype(String contacttype) {
        this.put(CONTACTTYPE,contacttype);
    }

}
