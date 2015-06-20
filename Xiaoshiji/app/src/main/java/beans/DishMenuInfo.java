package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by db on 6/19/15.
 */
@AVClassName(DishMenuInfo.DISHMENUINFO_CLASS)
public class DishMenuInfo extends AVObject{
    public static final String DISHMENUINFO_CLASS="dishmenu";
    public String DININGROOM_NAME = "diningroomname";
    public String MEAL_TYPE = "mealtype";
    public String MEAL_NAME = "mealname";
    public String MEAL_PRICE = "price";
    public void setDiningRoomName(String diningRoomName){
        this.put(DININGROOM_NAME,diningRoomName);
    }
    public String getDiningRoomName(){
        return this.getString(DININGROOM_NAME);
    }
    public void setMealType(String mealType){
        this.put(MEAL_TYPE,mealType);
    }
    public String getMealType(){
        return this.getString(MEAL_TYPE);
    }
    public void setMealName(String mealName){
        this.put(MEAL_NAME,mealName);
    }
    public String getMealName(){
        return this.getString(MEAL_NAME);
    }
    public void setMealPrice(String price){
        this.put(MEAL_PRICE,price);
    }
    public String getMealPrice(){
        return this.getString(MEAL_PRICE);
    }
}
