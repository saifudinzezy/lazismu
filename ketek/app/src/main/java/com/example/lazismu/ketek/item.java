package com.example.lazismu.ketek;

import android.os.Parcel;
import android.os.Parcelable;


class Item implements Parcelable{

    public String title;
    public String desciption;
    public boolean  isExpanded;

    public Item(){}

    public Item(Parcel in){
        title = in.readString();
        desciption = in.readString();
        isExpanded = in.readInt() == 1;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(desciption);
        dest.writeInt(isExpanded ? 1 : 0);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>(){
        @Override
        public Item createFromParcel(Parcel source){
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }

    };
}
