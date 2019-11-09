package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;

import com.MyFi.MyFridge.domain.entitiy.User;
import com.MyFi.MyFridge.httpConnect.HttpConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private HttpConnection httpConn = HttpConnection.getInstance();
    public User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("isNew", MODE_PRIVATE);

        if(sharedPreferences.getBoolean("isNew",true)) {
            //처음 접속한 사용자
            createUser();
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putBoolean("isNew",false);
            ed.commit();

        }
        else {
            // 기존 사용자
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    public void createUser() {
        new Thread() {
            public void run() {
                httpConn.createUser(user, userCallback);
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
            user = objectMapper.readValue(responseBytes, User.class);
            SharedPreferences sharedPreferences = getSharedPreferences("uid", MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putInt("uid",user.getUid());
            ed.commit();

            try{
                Thread.sleep(1500);
            } catch(Exception e){
            }


            //TODO: 연결되는 페이지를 초기설정 페이지로 수정
            Intent intent = new Intent(SplashActivity.this, InitialSettingActivity.class);
            startActivity(intent);
            finish();

            Message message = handler.obtainMessage();
            handler.sendMessage(message);


        }
    };

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {

        }
    };

}
