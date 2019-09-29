package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ImageView recipeDetailPic = findViewById(R.id.recipeDetailPic);
        TextView foodNameText = findViewById(R.id.foodNameText);
        TextView recipeIngredient = findViewById(R.id.recipeIngredient);
        TextView recipeTools = findViewById(R.id.recipeTools);

        // 레시피 보러가기 버튼
        Button recipeLinkButton = findViewById(R.id.recipeLinkButton);
        recipeLinkButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
