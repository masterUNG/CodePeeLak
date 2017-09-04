package com.student.codemobile.myauthendemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sitrach on 17/07/2017.
 */

class UserBean implements Parcelable {
    //Implement Parcelable ให้ Cursor อยู่ล่าง { ตรงนี้ แล้วกดเมนู Code เลือก Generate แล้วเลือก Parcelable มันจะ Gen โครงสร้างให้เอง
    public String username;
    public String password;
    public Integer isPasswordRemembered;

    public final static String TABLE_NAME = "USERPASSWORD";
    public final static String COLUMN_USERNAME = "USERNAME";
    public final static String COLUMN_PASSWORD = "PASSWORD";
    public final static String COLUMN_PASSWORD_REM = "PASSWORD_REM";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeValue(this.isPasswordRemembered);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.isPasswordRemembered = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
