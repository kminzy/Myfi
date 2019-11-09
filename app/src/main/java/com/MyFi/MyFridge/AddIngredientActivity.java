package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.MyFi.MyFridge.domain.entitiy.IngredientName_Code;
import com.MyFi.MyFridge.httpConnect.HttpConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AddIngredientActivity extends AppCompatActivity {
    //TODO:저장될 재료 객체
    IngredientData ingredient = new IngredientData();
    // 저장 가능한 재료 목록 (임시)
    private static final String[] ingredientName = new AutoCompleteText().getIngredientList();
    private HttpConnection httpConn = HttpConnection.getInstance();
    public IngredientName_Code ingredientName_code = new IngredientName_Code();
    public static Context aContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        aContext = this;
        //재료등록일 , 유통기한 초기화
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Date today = new Date();
        ingredient.setAdd_date(dateFormat.format(today));
        ingredient.setExp_date(dateFormat.format(today));


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

            }
        });

        nameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ingredient.setName(editable.toString());
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
                //Toast.makeText(AddIngredientActivity.this, date, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(AddIngredientActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(AddIngredientActivity.this, "location: " + (char)position, Toast.LENGTH_LONG).show();
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

                ingredient.setUid(((MainActivity)MainActivity.mContext).user.getUid());
                IngredientName_Code tmpic = new IngredientName_Code();
                tmpic.setName(ingredient.getName());

                nameToCode(tmpic);

            }
        });
    }




    public final void nameToCode(final IngredientName_Code tmpic) {
        new Thread() {
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("uid",MODE_PRIVATE);
                ((MainActivity)MainActivity.mContext).user.setUid(sharedPreferences.getInt("uid",1));
                httpConn.nameToCode(tmpic,ingredientCodeCallback);
            }
        }.start();
    }

    public void saveIngredient() {
        new Thread() {
            public void run() {
                httpConn.saveIngredient(((MainActivity) MainActivity.mContext).ingredient, ingredientCallback);
            }
        }.start();
    }


    public final Callback ingredientCodeCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).ingredientName_code = objectMapper.readValue(responseBytes,new TypeReference<IngredientName_Code>(){});
            ingredient.setCode(((MainActivity)MainActivity.mContext).ingredientName_code.getCode());
            ((MainActivity)MainActivity.mContext).ingredient.setIngredientData(ingredient);
            saveIngredient();
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
            ((MainActivity) MainActivity.mContext).ingredient = objectMapper.readValue(responseBytes, IngredientData.class);
            ((MainActivity)MainActivity.mContext).makeIngredientList();
            Intent intent = new Intent(AddIngredientActivity.this, ViewIngredientActivity.class);
            startActivity(intent);
            finish(); // 재료 추가 액티비티 종료


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
