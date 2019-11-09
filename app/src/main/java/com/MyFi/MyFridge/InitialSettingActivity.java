package com.MyFi.MyFridge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.MyFi.MyFridge.httpConnect.HttpConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.PreferenceChangeEvent;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InitialSettingActivity extends AppCompatActivity {
    public static Context context;
    public int uid;
    public int type;
    public char location;
    public String exp_date;
    public String add_date;

    private HttpConnection httpConn = HttpConnection.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setting);
        context = this;

        final CheckBox rangeCheck = findViewById(R.id.rangeCheck);
        final CheckBox airfryerCheck = findViewById(R.id.airfryerCheck);
        final CheckBox ovenCheck = findViewById(R.id.ovenCheck);

        final CheckBox redPepperPowderCheck = findViewById(R.id.redPepperPowderCheck);
        final CheckBox redPepperPasteCheck = findViewById(R.id.redPepperPasteCheck);
        final CheckBox soybeanPasteCheck = findViewById(R.id.soybeanPasteCheck);
        final CheckBox soySauceCheck = findViewById(R.id.soySauceCheck);
        final CheckBox sesameOilCheck = findViewById(R.id.sesameOilCheck);
        final CheckBox perillaOilCheck = findViewById(R.id.perillaOilCheck);
        final CheckBox honeyCheck = findViewById(R.id.honeyCheck);
        final CheckBox cornSyrupCheck = findViewById(R.id.cornSyrupCheck);
        final CheckBox oligodangCheck = findViewById(R.id.oligodangCheck);
        final CheckBox sugarCheck = findViewById(R.id.sugarCheck);
        final CheckBox saltCheck = findViewById(R.id.saltCheck);
        final CheckBox pepperCheck = findViewById(R.id.pepperCheck);

        SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);


        uid = sharedPreferences.getInt("uid",1);
        location = (char)99;
        type = 6;
        exp_date = "2999-12-31";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Date today = new Date();
        add_date=dateFormat.format(today);


        /*
    private int uid;
    private String name;
    private int code;
    private char location;
    private int type;
    private String add_date;
    private String exp_date;
        */

        Button initialButton = findViewById(R.id.initialButton);
        initialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences tools = PreferenceManager.getDefaultSharedPreferences(context);
                //SharedPreferences tools = getSharedPreferences("cookingTools",MODE_PRIVATE);
                SharedPreferences.Editor ed = tools.edit();
                Set<String> toolSet = new HashSet<String>();


                if(rangeCheck.isChecked()) {
                    toolSet.add("1");
                }
                if(airfryerCheck.isChecked()){
                    toolSet.add("2");
                }
                if(ovenCheck.isChecked()) {
                    toolSet.add("3");
                }

                ed.putStringSet("cookingTools",toolSet);
                ed.commit();

                if(redPepperPowderCheck.isChecked()) {
                    IngredientData ingredient1 = new IngredientData();
                    ingredient1.setUid(uid);
                    ingredient1.setType(type);
                    ingredient1.setLocation(location);
                    ingredient1.setExp_date(exp_date);
                    ingredient1.setAdd_date(add_date);
                    ingredient1.setName("고춧가루");
                    ingredient1.setCode(105);
                    saveIngredient(ingredient1);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(redPepperPasteCheck.isChecked()){
                    IngredientData ingredient2 = new IngredientData();
                    ingredient2.setUid(uid);
                    ingredient2.setType(type);
                    ingredient2.setLocation(location);
                    ingredient2.setExp_date(exp_date);
                    ingredient2.setAdd_date(add_date);
                    ingredient2.setName("고추장");
                    ingredient2.setCode(104);
                    saveIngredient(ingredient2);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }

                }
                if(soybeanPasteCheck.isChecked()) {
                    IngredientData ingredient3 = new IngredientData();
                    ingredient3.setUid(uid);
                    ingredient3.setType(type);
                    ingredient3.setLocation(location);
                    ingredient3.setExp_date(exp_date);
                    ingredient3.setAdd_date(add_date);
                    ingredient3.setName("된장");
                    ingredient3.setCode(31);
                    saveIngredient(ingredient3);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(soySauceCheck.isChecked()) {
                    IngredientData ingredient4 = new IngredientData();
                    ingredient4.setUid(uid);
                    ingredient4.setType(type);
                    ingredient4.setLocation(location);
                    ingredient4.setExp_date(exp_date);
                    ingredient4.setAdd_date(add_date);
                    ingredient4.setName("간장");
                    ingredient4.setCode(101);
                    saveIngredient(ingredient4);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(sesameOilCheck.isChecked()) {
                    IngredientData ingredient5 = new IngredientData();
                    ingredient5.setUid(uid);
                    ingredient5.setType(type);
                    ingredient5.setLocation(location);
                    ingredient5.setExp_date(exp_date);
                    ingredient5.setAdd_date(add_date);
                    ingredient5.setName("참기름");
                    ingredient5.setCode(130);
                    saveIngredient(ingredient5);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(perillaOilCheck.isChecked()) {
                    IngredientData ingredient6 = new IngredientData();
                    ingredient6.setUid(uid);
                    ingredient6.setType(type);
                    ingredient6.setLocation(location);
                    ingredient6.setExp_date(exp_date);
                    ingredient6.setAdd_date(add_date);
                    ingredient6.setName("들기름");
                    ingredient6.setCode(150);
                    saveIngredient(ingredient6);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(honeyCheck.isChecked()) {
                    IngredientData ingredient7 = new IngredientData();
                    ingredient7.setUid(uid);
                    ingredient7.setType(type);
                    ingredient7.setLocation(location);
                    ingredient7.setExp_date(exp_date);
                    ingredient7.setAdd_date(add_date);
                    ingredient7.setName("꿀");
                    ingredient7.setCode(108);
                    saveIngredient(ingredient7);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(cornSyrupCheck.isChecked()) {
                    IngredientData ingredient8 = new IngredientData();
                    ingredient8.setUid(uid);
                    ingredient8.setType(type);
                    ingredient8.setLocation(location);
                    ingredient8.setExp_date(exp_date);
                    ingredient8.setAdd_date(add_date);
                    ingredient8.setName("물엿");
                    ingredient8.setCode(119);
                    saveIngredient(ingredient8);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(oligodangCheck.isChecked()) {
                    IngredientData ingredient9 = new IngredientData();
                    ingredient9.setUid(uid);
                    ingredient9.setType(type);
                    ingredient9.setLocation(location);
                    ingredient9.setExp_date(exp_date);
                    ingredient9.setAdd_date(add_date);
                    ingredient9.setName("올리고당");
                    ingredient9.setCode(128);
                    saveIngredient(ingredient9);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(sugarCheck.isChecked()) {
                    IngredientData ingredient10 = new IngredientData();
                    ingredient10.setUid(uid);
                    ingredient10.setType(type);
                    ingredient10.setLocation(location);
                    ingredient10.setExp_date(exp_date);
                    ingredient10.setAdd_date(add_date);
                    ingredient10.setName("설탕");
                    ingredient10.setCode(126);
                    saveIngredient(ingredient10);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(saltCheck.isChecked()) {
                    IngredientData ingredient11 = new IngredientData();
                    ingredient11.setUid(uid);
                    ingredient11.setType(type);
                    ingredient11.setLocation(location);
                    ingredient11.setExp_date(exp_date);
                    ingredient11.setAdd_date(add_date);
                    ingredient11.setName("소금");
                    ingredient11.setCode(127);
                    saveIngredient(ingredient11);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }
                if(pepperCheck.isChecked()) {
                    IngredientData ingredient12 = new IngredientData();
                    ingredient12.setUid(uid);
                    ingredient12.setType(type);
                    ingredient12.setLocation(location);
                    ingredient12.setExp_date(exp_date);
                    ingredient12.setAdd_date(add_date);
                    ingredient12.setName("후추");
                    ingredient12.setCode(138);
                    saveIngredient(ingredient12);
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e){
                    }
                }

                Intent intent = new Intent(InitialSettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void saveIngredient(final IngredientData ingredient) {
        new Thread() {
            public void run() {
                httpConn.saveIngredient(ingredient, ingredientCallback);
            }
        }.start();
    }

    public final Callback ingredientCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
        }
    };
}
