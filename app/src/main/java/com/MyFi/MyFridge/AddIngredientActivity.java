package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

public class AddIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // 유통기한 날짜 선택
        DatePicker date = findViewById(R.id.datePicker);
        // 오늘 날짜로 초기화
        date.init(date.getYear(), date.getMonth(), date.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 날짜 토스트로 띄움 (임시)
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                Toast.makeText(AddIngredientActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });

        // 식품유형 선택
        Spinner categorySpinner = findViewById(R.id.pickCategory);
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this, R.array.ingredientCategory, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // 보관장소 선택
        Spinner placeSpinner = findViewById(R.id.pickPlace);
        ArrayAdapter placeAdapter = ArrayAdapter.createFromResource(this, R.array.ingredientPlace, android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);

        // 완료 버튼
        Button addCompleteButton = findViewById(R.id.addCompleteButton);
        addCompleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish(); // 재료 추가 액티비티 종료
            }
        });
    }
}
