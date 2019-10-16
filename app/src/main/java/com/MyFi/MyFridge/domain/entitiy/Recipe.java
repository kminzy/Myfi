package com.MyFi.MyFridge.domain.entitiy;


import lombok.Data;

@Data
public class Recipe {

    private int rid;
    private String name;
    private int tool;
    private int recipe_code;
    private String needed;
}
