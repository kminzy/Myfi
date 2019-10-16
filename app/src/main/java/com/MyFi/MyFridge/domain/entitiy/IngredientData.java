package com.MyFi.MyFridge.domain.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import lombok.Data;

@Data
public class IngredientData implements Parcelable {
    private int iid; // Auto Increase
    private int uid;
    private String name;
    private int code;
    private char location;
    private int type;
    private String add_date;
    private String exp_date;

    public IngredientData() {
        int iid; // Auto Increase
        int uid;
        String name;
        int code;
        char location;
        int type;
        String add_date;
        String exp_date;
    }

    protected IngredientData(int iid, int uid, String name, int code, char location, int type, String add_date, String exp_date)
    {
        this.iid = iid;
        this.uid = uid;
        this.name = name;
        this.code = code;
        this.location = location;
        this.type = type;
        this.add_date = add_date;
        this.exp_date = exp_date;
    }

    public static final Creator<IngredientData> CREATOR = new Creator<IngredientData>() {
        @Override
        public IngredientData createFromParcel(Parcel parcel) {
            return new IngredientData(parcel);
        }

        @Override
        public IngredientData[] newArray(int i) {
            return new IngredientData[i];
        }
    };

    public IngredientData(Parcel parcel) {
        this.iid = parcel.readInt();
        this.uid = parcel.readInt();
        this.name = parcel.readString();
        this.code = parcel.readInt();
        this.location = parcel.readString().toCharArray()[0];
        this.type = parcel.readInt();
        this.add_date = parcel.readString();
        this.exp_date = parcel.readString();
    }



    public void setIngredientData(IngredientData ingredientData) {
        this.iid = ingredientData.getIid();
        this.uid = ingredientData.getUid();
        this.name = ingredientData.getName();
        this.code = ingredientData.getCode();
        this.location = ingredientData.getLocation();
        this.type = ingredientData.getType();
        this.add_date = ingredientData.getAdd_date();
        this.exp_date = ingredientData.getExp_date();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(iid);
        parcel.writeInt(uid);
        parcel.writeString(name);
        parcel.writeInt(code);
        parcel.writeString(location+"");
        parcel.writeInt(type);
        parcel.writeString(add_date);
        parcel.writeString(exp_date);


    }
}