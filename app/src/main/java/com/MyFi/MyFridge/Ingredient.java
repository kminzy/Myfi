package com.MyFi.MyFridge;

//TODO:클래스 삭제 필요 IngredientData로 대체
public class Ingredient {

    private String ingredientName;
    private String dDay;
    private String expirationDate;

    public Ingredient(String ingredientName, String dDay, String expirationDate) {
        this.ingredientName = ingredientName;
        this.dDay = dDay;
        this.expirationDate = expirationDate;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getDDay() {
        return dDay;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
