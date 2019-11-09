package com.MyFi.MyFridge.domain.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecipeDto implements Comparable<RecipeDto> {
    public String name;
    private String link;
    public List<Integer> needed;
    public List<String> neededString;
    public List<String> having;
    private int numberOfnone;

    public RecipeDto()
    {
        String name;
        String link;
        List<Integer> needed;
        List<String> neededString;
        List<String> having;
        int numberOfnone;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNeeded(List<Integer> needed){
        this.needed = needed;
    }

    public void setNeededString(List<String> neededString){
        this.neededString = neededString;
    }

    public void setHaving(List<String> having) { this.having = having; }

    public void setNumberOfnone(int numberOfnone){
        this.numberOfnone = numberOfnone;
    }

    public  void setLink(String link) { this.link = link; }

    public List<Integer> getNeeded() {return this.needed;}

    public int getNumberOfnone(){
        return this.numberOfnone;
    }

    public String getName() { return  this.name;}

    public List<String> getNeededString() { return this.neededString; }

    public String getLink() { return this.link; }

    public List<String> getNone() {

        List<String> result = new ArrayList<>();
        result.addAll(this.neededString);
        result.removeAll(this.having);

        return result;
    }

    @Override
    public int compareTo(RecipeDto r) {
        // TODO Auto-generated method stub

        if (this.numberOfnone < r.getNumberOfnone())
        {
            return -1;
        }
        else if(this.numberOfnone > r.getNumberOfnone())
        {
            return 1;
        }
        return 0;
    }
}