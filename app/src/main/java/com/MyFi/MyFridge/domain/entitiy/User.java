package com.MyFi.MyFridge.domain.entitiy;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class User {
    private int uid;
    private List<IngredientData> ingredientList= new ArrayList<>();

    public void addIngredientList(IngredientData ingredient) {
        this.ingredientList.add(ingredient);
    }
    public void deleteIngredientList(IngredientData ingredient) { this.ingredientList.remove(ingredient);}


}