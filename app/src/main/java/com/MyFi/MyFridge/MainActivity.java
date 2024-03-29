package com.MyFi.MyFridge;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.MyFi.MyFridge.domain.dto.RecipeDto;
import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.MyFi.MyFridge.domain.entitiy.IngredientName_Code;
import com.MyFi.MyFridge.domain.entitiy.Recipe;
import com.MyFi.MyFridge.domain.entitiy.User;
import com.MyFi.MyFridge.httpConnect.HttpConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //TODO: 서버 접근 메소드 결과 임시 저장 객체
    private HttpConnection httpConn = HttpConnection.getInstance();
    public User user = new User();
    public IngredientData ingredient = new IngredientData();
    public RecipeDto recipeDto = new RecipeDto();
    public IngredientName_Code ingredientName_code = new IngredientName_Code();
    //public List<RecipeDto> hatedRecipes = new ArrayList<>();
    public static List<IngredientData> myIngredientList = new ArrayList<>();
    public static List<RecipeDto> recommendedRecipes = new ArrayList<>();

    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO:다른 액티비티에서 메인 메소드 접근을 위한 CONTEXT 및 유저 재료리스트 초기화 (확인 필요)
        mContext = this;

        SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
        user.setUid(sharedPreferences.getInt("uid",1));

        makeIngredientList();

        setContentView(R.layout.activity_main);
        // 냉장고 관리 버튼
        Button setFridge = findViewById(R.id.setFridge);
        setFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewIngredientActivity.class);
                startActivity(intent);
            }
        });

        // 레시피 추천 버튼
        Button viewRecipe = findViewById(R.id.viewRecipe);
        viewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences tools = PreferenceManager.getDefaultSharedPreferences((MainActivity)MainActivity.mContext);
                Set<String> test = new HashSet<String>();
                test.add("1");

                getRecommendedRecipes((tools.getStringSet("cookingTools",test)).toString());
            }
        });

        // 설정 버튼
        ImageButton settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }


    long backPressed; // 뒤로가기 버튼 누른 시간

    @Override
    public void onBackPressed() {
        // 뒤로가기 버튼 1번 누름
        if(System.currentTimeMillis() > backPressed + 2000) {
            backPressed = System.currentTimeMillis();
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
        } else  // 뒤로가기 버튼 2번 누름
            finishApp();
    }

    // 앱 종료
    public void finishApp() {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }





    //TODO:서버 접근 메소드 및 콜백메소드
    public void getUserInfo() {
        new Thread() {
            public void run() {
                ((MainActivity)MainActivity.mContext).user.setUid(1);
                httpConn.getUserInfo(((MainActivity)MainActivity.mContext).user, userCallback);
            }
        }.start();
    }

    public void saveIngredient() {
        new Thread() {
            public void run() {
                httpConn.saveIngredient(((MainActivity)MainActivity.mContext).ingredient, ingredientCallback);
            }
        }.start();
    }

    public void deleteIngredient() {
        new Thread() {
            public void run() {
                httpConn.deleteIngredient(((MainActivity)MainActivity.mContext).ingredient, ingredientCallback);
            }
        }.start();
    }

    public void makeIngredientList() {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.makeIngredientList(user,ingredientListCallback);
            }
        }.start();
    }

    public final void makeIngredientListByType(final int type) {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.makeIngredientListByType(user,type,ingredientListCallback);
            }
        }.start();
    }

    public final void makeIngredientListByLocation(final char location) {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.makeIngredientListByLocation(user,location,ingredientListCallback);
            }
        }.start();
    }


    public final void nameToCode(final IngredientName_Code tmpic) {
        new Thread() {
            public void run() {
                httpConn.nameToCode(tmpic,ingredientCodeCallback);
            }
        }.start();
    }

    public final void getRecommendedRecipes(final String tools) {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.getRecommendedRecipe(user,tools,recommendCallback);
            }
        }.start();
    }




    public final Callback userCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).user = objectMapper.readValue(responseBytes, User.class);

            Message message = handler.obtainMessage();
            handler.sendMessage(message);

        }
    };
    public final Callback ingredientCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).ingredient = objectMapper.readValue(responseBytes, IngredientData.class);
        }
    };


    public final Callback ingredientListCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).myIngredientList = objectMapper.readValue(responseBytes,new TypeReference<List<IngredientData>>(){});
            Collections.sort(((MainActivity)MainActivity.mContext).myIngredientList);

        }
    };

    public final Callback ingredientCodeCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).ingredientName_code = objectMapper.readValue(responseBytes,new TypeReference<IngredientName_Code>(){});

        }
    };

    public final Callback recommendCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).recommendedRecipes = objectMapper.readValue(responseBytes,new TypeReference<List<RecipeDto>>(){});
            Intent intent = new Intent(MainActivity.this, ViewRecipeListActivity.class);
            startActivity(intent);
        }
    };



    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {

        }
    };


    public List<IngredientData> callIngredientList(){
        try{
            Thread.sleep(1000);
        } catch(Exception e){
        }
        return myIngredientList;
    }



}

