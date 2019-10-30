package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;

import java.text.SimpleDateFormat;

public class AddIngredientActivity extends AppCompatActivity {
    //TODO:저장될 재료 객체
    IngredientData ingredient = new IngredientData();
    // 저장 가능한 재료 목록 (임시)
    String[] ingredientName = new String[] {
            "고사리", "고추", "돼지고기", "두릅", "두부", "마늘", "배추", "소고기", "식초", "생선", "양파", "양상추", "양배추"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // 재료 이름 입력
        final AutoCompleteTextView nameTextView = (AutoCompleteTextView)findViewById(R.id.autoIngredientSearch);
        final ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ingredientName);
        nameTextView.setAdapter(nameAdapter);
        // 재료 이름 선택 시 재료 객체에 저장
        nameTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 재료 이름 선택 시 키보드 내리기
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nameTextView.getWindowToken(), 0);

                // 선택된 재료 객체에 저장
                String selectedName = nameAdapter.getItem(position).toString();
                ingredient.setName(selectedName);
                Toast.makeText(AddIngredientActivity.this, "ingredient: " + selectedName, Toast.LENGTH_SHORT).show();
            }
        });

        // 유통기한 날짜 선택
        DatePicker date = findViewById(R.id.datePicker);
        date.init(date.getYear(), date.getMonth(), date.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // monthOfYear 수정
                monthOfYear += 1;
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
        // 식품유형 선택 시 재료 객체에 저장
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 목록의 위치를 식품유형 코드에 저장
                position += 1;
                ingredient.setType(position);
                Toast.makeText(AddIngredientActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 보관장소 선택
        Spinner placeSpinner = findViewById(R.id.pickPlace);
        ArrayAdapter placeAdapter = ArrayAdapter.createFromResource(this, R.array.ingredientPlace, android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);
        // 보관장소 선택 시 재료 객체에 저장
        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택한 위치를 문자형으로 변경
                position += 97;
                ingredient.setLocation((char)position);
                Toast.makeText(AddIngredientActivity.this, "location: " + (char)position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 완료 버튼
        Button addCompleteButton = findViewById(R.id.addCompleteButton);
        addCompleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredient.setCode(11);
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
