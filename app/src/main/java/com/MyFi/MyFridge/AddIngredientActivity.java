package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;

public class AddIngredientActivity extends AppCompatActivity {
    //TODO:저장될 재료 객체
    IngredientData ingredient = new IngredientData();

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
                String date = year + "-" + monthOfYear + "-" + dayOfMonth;
                //TODO: 재료 유통기한 추가
                ingredient.setExp_date(date);
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

                //TODO:재료 저장 임시 생성
                ingredient.setAdd_date("2019-11-11"); //Today to String으로 변경
                ingredient.setCode(11);
                ingredient.setLocation('a');
                ingredient.setType(1);
                ingredient.setName("안드로드재료");
                ingredient.setUid(1);
                ((MainActivity)MainActivity.mContext).ingredient.setIngredientData(ingredient);
                ((MainActivity)MainActivity.mContext).ingredient.setUid(1);
                //TODO: 저장할 재료 정보 서버에 전달
                ((MainActivity)MainActivity.mContext).saveIngredient();

                //TODO: 재료목록 생성을 위한 재료 데이터 이전 액티비티로의 전달
                Intent intent = new Intent();
                intent.putExtra("result",ingredient);

                setResult(RESULT_OK,intent);
                finish(); // 재료 추가 액티비티 종료


            }
        });
    }
}
