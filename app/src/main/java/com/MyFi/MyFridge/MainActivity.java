package com.MyFi.MyFridge;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent intent = new Intent(MainActivity.this, ViewRecipeListActivity.class);
                startActivity(intent);
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
}
