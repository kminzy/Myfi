package com.MyFi.MyFridge.domain.dto;

import java.util.List;

public class RecipeDto implements Comparable<RecipeDto> {
    public String name;
    public List<Integer> needed;
    public List<String> neededString;
    public List<String> having;
    private int numberOfnone;

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

    public List<Integer> getNeeded() {return this.needed;}

    public int getNumberOfnone(){
        return this.numberOfnone;
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