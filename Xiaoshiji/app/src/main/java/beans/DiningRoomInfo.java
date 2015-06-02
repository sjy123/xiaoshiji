package beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by db on 6/2/15.
 */
public class DiningRoomInfo implements Parcelable {

    private String title;
    private String address;
    private String category;
    private String type;
    private String tel;
    private String id;
    private String location;
    private String pano;

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getTel() {
        return tel;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getPano() {
        return pano;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPano(String pano) {
        this.pano = pano;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public DiningRoomInfo(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeString(this.category);
        dest.writeString(this.type);
        dest.writeString(this.tel);
        dest.writeString(this.id);
        dest.writeString(this.location);
        dest.writeString(this.pano);
    }

    private DiningRoomInfo(Parcel in) {
        this.title = in.readString();
        this.address = in.readString();
        this.category = in.readString();
        this.type = in.readString();
        this.tel = in.readString();
        this.id = in.readString();
        this.location = in.readString();
        this.pano = in.readString();
    }

    public static final Parcelable.Creator<DiningRoomInfo> CREATOR = new Parcelable.Creator<DiningRoomInfo>() {
        public DiningRoomInfo createFromParcel(Parcel source) {
            return new DiningRoomInfo(source);
        }

        public DiningRoomInfo[] newArray(int size) {
            return new DiningRoomInfo[size];
        }
    };
}
