package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
        TextView recipeIngredient1 = findViewById(R.id.recipeIngredient1);
        TextView recipeIngredient2 = findViewById(R.id.recipeIngredient2);
        TextView recipeIngredient3 = findViewById(R.id.recipeIngredient3);
        TextView recipeIngredient4 = findViewById(R.id.recipeIngredient4);
        TextView recipeIngredient5 = findViewById(R.id.recipeIngredient5);
        TextView recipeIngredient6 = findViewById(R.id.recipeIngredient6);
        TextView recipeIngredient7 = findViewById(R.id.recipeIngredient7);
        TextView recipeIngredient8 = findViewById(R.id.recipeIngredient8);
        TextView recipeIngredient9 = findViewById(R.id.recipeIngredient9);
        TextView recipeIngredient10 = findViewById(R.id.recipeIngredient10);
        TextView recipeIngredient11 = findViewById(R.id.recipeIngredient11);
        TextView recipeIngredient12 = findViewById(R.id.recipeIngredient12);
        TextView recipeIngredient13 = findViewById(R.id.recipeIngredient13);
        TextView recipeIngredient14 = findViewById(R.id.recipeIngredient14);
        TextView recipeIngredient15 = findViewById(R.id.recipeIngredient15);
        TextView recipeIngredient16 = findViewById(R.id.recipeIngredient16);

        TextView[] textViews = new TextView[16];
        textViews[0] = recipeIngredient1;
        textViews[1] = recipeIngredient2;
        textViews[2] = recipeIngredient3;
        textViews[3] = recipeIngredient4;
        textViews[4] = recipeIngredient5;
        textViews[5] = recipeIngredient6;
        textViews[6] = recipeIngredient7;
        textViews[7] = recipeIngredient8;
        textViews[8] = recipeIngredient9;
        textViews[9] = recipeIngredient10;
        textViews[10] = recipeIngredient11;
        textViews[11] = recipeIngredient12;
        textViews[12] = recipeIngredient13;
        textViews[13] = recipeIngredient14;
        textViews[14] = recipeIngredient15;
        textViews[15] = recipeIngredient16;

        List<String> needed = ((MainActivity)MainActivity.mContext).recipeDto.getNeededString();

        for(int i=0; i<needed.size(); i++)
        {
            textViews[i].setText("- "+ needed.get(i));

            if(((MainActivity)MainActivity.mContext).recipeDto.getNone().contains(needed.get(i)))
            {
                textViews[i].setTextColor(Color.parseColor("#FF0000"));
            }
        }



        ImageView recipeImage = findViewById(R.id.recipeDetailPic);
        String imgName = "recipe"+((MainActivity)MainActivity.mContext).recipeDto.getRid();
        int resourceId = ((MainActivity)MainActivity.mContext).getResources().getIdentifier(imgName, "drawable", ((MainActivity)MainActivity.mContext).getPackageName());
        recipeImage.setImageResource(resourceId);
        foodNameText.setText(((MainActivity)MainActivity.mContext).recipeDto.getName());


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

        final Button hateButton = findViewById(R.id.hate_button);
        hateButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                addHatedUser(((MainActivity)MainActivity.mContext).recipeDto.getRid());
                Toast.makeText(getApplicationContext(),((MainActivity)MainActivity.mContext).recipeDto.getName()+ "을(를) 더이상 추천 받지 않습니다.", Toast.LENGTH_LONG).show();
                hateButton.setEnabled(false);


            }
        });



    }

    public final void addHatedUser(final int rid) {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.addHatedUser(((MainActivity)MainActivity.mContext).user, rid, addHatedCallback);
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
