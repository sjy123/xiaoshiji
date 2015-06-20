package beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by db on 6/20/15.
 */
@AVClassName(CommitInfo.COMMITINFO_CLASS)
public class CommitInfo extends AVObject {
    public static final String COMMITINFO_CLASS="commit";
    public String CONTENT = "content";
    public String DATE = "date";
    /*
    user就用他的手机号码匹配
     */
    public String USER = "user";
    public String DININGROOMNAME = "diningroomname";
    public void setContent(String content){
        this.put(CONTENT,content);
    }
    public String getContent(){
        return this.getString(CONTENT);
    }
    public void setDate(String date){
        this.put(DATE,date);
    }
    public String getDate(){
        return this.getString(DATE);
    }
    public void setUser(String user){
        this.put(USER,user);
    }
    public String getUser(){
        return this.getString(USER);
    }
    public void setDiningRoomName(String diningRoomName){
        this.put(DININGROOMNAME,diningRoomName);
    }
    public String getDiningRoomName(){
        return this.getString(DININGROOMNAME);
    }
}
