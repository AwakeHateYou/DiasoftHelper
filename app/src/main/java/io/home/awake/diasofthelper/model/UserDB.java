package io.home.awake.diasofthelper.model;

import android.os.Parcel;
import android.os.Parcelable;


public class UserDB implements Parcelable{
    int id;
    long userid;

    public UserDB(){
        this.id = 0;
    }


    public UserDB(int id, long userid){
        this.id = id;
        this.userid = userid;
    }


    public UserDB(Parcel in){
        id = in.readInt();
        userid = in.readLong();
    }


    public static final Creator<UserDB> CREATOR = new Creator<UserDB>() {
        @Override
        public UserDB createFromParcel(Parcel in) {
            return new UserDB(in);
        }

        @Override
        public UserDB[] newArray(int size) {
            return new UserDB[size];
        }
    };
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public Long getUserID() {
        return userid;
    }

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(userid);
    }
    @Override
    public int describeContents() {
        return 0;
    }

}