package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Jay on 15-6-21.
 */
@AVClassName("personaldishesinfo")
public class PersonalDishesInfo extends AVObject {
    public String MEALNAME = "mealname";
    public String MEALPRICE = "mealprice";
    public String USER = "user";
    public String DININGROOMNAME = "diningroomname";
    public String LIKE="like";
    public void setLike(boolean isLike){
        put(LIKE,isLike);
    }

    public boolean getLIKE() {
        return getBoolean(LIKE);
    }

    public void setMEALNAME(String mealname) {
        put(MEALNAME, mealname);
    }

    public String getMEALNAME() {
        return getString(MEALNAME);
    }

    public void setMEALPRICE(String mealprice) {
        put(MEALPRICE, mealprice);
    }

    public String getMEALPRICE() {
        return getString(MEALPRICE);
    }

    public void setUser(String user) {
        this.put(USER, user);
    }

    public String getUser() {
        return this.getString(USER);
    }

    public void setDiningRoomName(String diningRoomName) {
        this.put(DININGROOMNAME, diningRoomName);
    }

    public String getDiningRoomName() {
        return this.getString(DININGROOMNAME);
    }
}
