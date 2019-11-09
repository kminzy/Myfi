package com.MyFi.MyFridge.domain.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lombok.Data;

@Data
public class IngredientData implements Comparable<IngredientData> {
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

    protected IngredientData(int iid, int uid, String name, int code, char location, int type, String add_date, String exp_date) {
        this.iid = iid;
        this.uid = uid;
        this.name = name;
        this.code = code;
        this.location = location;
        this.type = type;
        this.add_date = add_date;
        this.exp_date = exp_date;
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

    public long getDday(){
        long dday= 0;

        try {
            Calendar todayCal = Calendar.getInstance();
            Calendar expdateCal = Calendar.getInstance();


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            expdateCal.setTime(simpleDateFormat.parse(exp_date));

            long startDate = todayCal.getTimeInMillis() / (24 * 60 * 60 * 1000);
            long endDate = expdateCal.getTimeInMillis() / (24 * 60 * 60 * 1000);
            dday = endDate - startDate;
        }

        catch(ParseException e) {
            e.printStackTrace();
        }
        return dday;
    }



    @Override
    public int compareTo(IngredientData i) {
        if (this.getDday() < i.getDday())
        {
            return -1;
        }
        else if(this.getDday() > i.getDday())
        {
            return 1;
        }
        return 0;
    }
}
