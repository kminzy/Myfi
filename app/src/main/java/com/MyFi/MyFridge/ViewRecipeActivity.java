package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.MyFi.MyFridge.domain.entitiy.Recipe;

public class ViewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ImageView recipeDetailPic = findViewById(R.id.recipeDetailPic);
        TextView foodNameText = findViewById(R.id.foodNameText);
        TextView recipeIngredient = findViewById(R.id.recipeIngredient);
        TextView recipeIngredientList = findViewById(R.id.recipeIngredientList);
        TextView recipeTools = findViewById(R.id.recipeTools);
        TextView recipeToolsList = findViewById(R.id.recipeToolsList);

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
    }
}
