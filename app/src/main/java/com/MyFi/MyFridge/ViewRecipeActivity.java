package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.MyFi.MyFridge.domain.dto.RecipeDto;
import com.MyFi.MyFridge.domain.entitiy.User;
import com.MyFi.MyFridge.httpConnect.HttpConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ViewRecipeActivity extends AppCompatActivity {

    private HttpConnection httpConn = HttpConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ImageView recipeDetailPic = findViewById(R.id.recipeDetailPic);
        TextView foodNameText = findViewById(R.id.foodNameText);
        TextView recipeIngredient = findViewById(R.id.recipeIngredient);
        TextView recipeTools = findViewById(R.id.recipeTools);

        foodNameText.setText(((MainActivity)MainActivity.mContext).recipeDto.getName());
        recipeIngredient.setText("재료 : "+ ((MainActivity)MainActivity.mContext).recipeDto.getNeededString());
        recipeTools.setText("없는 재료 : " + ((MainActivity)MainActivity.mContext).recipeDto.getNone());

        // 레시피 보러가기 버튼
        Button recipeLinkButton = findViewById(R.id.recipeLinkButton);
        recipeLinkButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context c = v.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(((MainActivity)MainActivity.mContext).recipeDto.getLink()));

                try {
                    c.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Button hateButton = findViewById(R.id.hate_button);
        hateButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                addHatedUser(((MainActivity)MainActivity.mContext).recipeDto.getName());

            }
        });



    }

    public final void addHatedUser(final String recipeName) {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.addHatedUser(((MainActivity)MainActivity.mContext).user, recipeName, addHatedCallback);
            }
        }.start();
    }

    public final Callback addHatedCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    };


}
