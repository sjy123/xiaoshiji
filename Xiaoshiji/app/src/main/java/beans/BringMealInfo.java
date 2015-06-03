package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by db on 6/2/15.
 */
@AVClassName(BringMealInfo.BRINGMEALINFO_CLASS)
public class BringMealInfo extends AVObject{
    public static final String BRINGMEALINFO_CLASS="bringMealInfo";
    private String mealname="mealname";
    private String mealtype="mealtype";
    private String destination="destination";
    private String paytype="paytype";
    private String contacttype="contacttype";

    public String getMealname() {
        return this.getString(mealname);
    }

    public void setMealname(String mealname) {
        this.put(mealname,mealname);
    }

    public String getMealtype() {
        return this.getString(mealtype);
    }

    public void setMealtype(String mealtype) {
        this.put(mealtype,mealtype);
    }

    public String getDestination() {
        return this.getString(destination);
    }

    public void setDestination(String destination) {
        this.put(destination,destination);
    }

    public String getPaytype() {
        return this.getString(paytype);
    }

    public void setPaytype(String paytype) {
        this.put(paytype,paytype);
    }

    public String getContacttype() {
        return this.getString(contacttype);
    }

    public void setContacttype(String contacttype) {
        this.put(contacttype,contacttype);
    }

}
