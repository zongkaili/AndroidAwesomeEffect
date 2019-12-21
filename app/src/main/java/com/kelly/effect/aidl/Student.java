package com.kelly.effect.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: zongkaili
 * data: 2018/10/6
 * desc:
 */
public class Student implements Parcelable {
    public static String name = "BOB";
    public int s_id;
    public String s_name;
    public String s_gender;

    public Student(Parcel in) {
        s_id = in.readInt();
        s_name = in.readString();
        s_gender = in.readString();
    }

    public Student(int s_id, String s_name, String s_gender) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_gender = s_gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(s_id);
        dest.writeString(s_name);
        dest.writeString(s_gender);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public String toString() {
        return String.format("[StudentID: %s , StudentName: %s , StudentGender: %s]", s_id, s_name, s_gender);
    }
}
