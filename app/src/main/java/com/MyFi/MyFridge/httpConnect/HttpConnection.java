package com.MyFi.MyFridge.httpConnect;


import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.MyFi.MyFridge.domain.entitiy.IngredientName_Code;
import com.MyFi.MyFridge.domain.entitiy.User;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {
    private final String url = "http://192.168.0.3:8080";
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection(){ this.client = new OkHttpClient(); }

    public void getUserInfo(User user, Callback callback) {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        Request request = new Request.Builder()
                .url(url+"/userinfo")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void createUser(User user, Callback callback) {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        Request request = new Request.Builder()
                .url(url+"/createuser")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        client.newCall(request).enqueue(callback);

    }


    public void saveIngredient(IngredientData ingredient, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(ingredient);

        Request request = new Request.Builder()
                .url(url+"/saveingredient")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();


        client.newCall(request).enqueue(callback);
    }


    public void deleteIngredient(IngredientData ingredient, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(ingredient);

        Request request = new Request.Builder()
                .url(url+"/deleteingredient")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();


        client.newCall(request).enqueue(callback);
    }

    public void makeIngredientList(User user, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(user);

        Request request = new Request.Builder()
                .url(url+"/makeingredientlist")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void makeIngredientListByType(User user,int type, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(user);

        Request request = new Request.Builder()
                .url(url+"/makeingredientlistByType/"+type)
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void makeIngredientListByLocation(User user,char location, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(user);

        Request request = new Request.Builder()
                .url(url+"/makeingredientlistByLocation/"+location)
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void nameToCode(IngredientName_Code ingredientName_code, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(ingredientName_code);

        Request request = new Request.Builder()
                .url(url+"/nametocode")
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void  getRecommendedRecipe(User user, int[] tools, Callback callback) {

        Gson gson = new Gson();
        String json = gson.toJson(user);

        String tool = Arrays.toString(tools);
        String tools_ = tool.substring(1,tool.length()-1);

        Request request = new Request.Builder()
                .url(url+"/getrecommendedrecipelist/"+tools_)
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(request).enqueue(callback);

    }





}
