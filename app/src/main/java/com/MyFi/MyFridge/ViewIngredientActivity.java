package com.MyFi.MyFridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.MyFi.MyFridge.domain.entitiy.User;
import com.MyFi.MyFridge.httpConnect.HttpConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ViewIngredientActivity extends AppCompatActivity {

    public static Context mContext;
    final IngredientAdapter adapter = new IngredientAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ingredient);

        mContext = this;


        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // 전체 버튼
        Button allButton = findViewById(R.id.allButton);
        allButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전체 선택 시 화면의 텍스트뷰에 반영
                TextView selectedText = findViewById(R.id.selectedText);
                selectedText.setText("나의 재료 목록");
                ((MainActivity)MainActivity.mContext).makeIngredientList();
                recreate();

            }
        });

        // 유형별 버튼
        Button categoryButton = findViewById(R.id.categoryButton);
        categoryButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu categoryMenu = new PopupMenu(getApplicationContext(), v);

                getMenuInflater().inflate(R.menu.popupmenu_category, categoryMenu.getMenu());
                categoryMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        TextView selectedText = findViewById(R.id.selectedText);

                        // 각 카테고리 선택 시 화면에 반영
                        switch (item.getItemId()) {
                            case R.id.meat:
                                //TODO: 재료 분류 선택시 이에 해당하는 재료 목록 호출
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(1);
                                //selectedText.setText("육류 목록");
                                break;
                            case R.id.fish:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(2);
                                //selectedText.setText("어패류 목록");
                                break;
                            case R.id.fruitVegetable:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(3);
                                //selectedText.setText("과일 및 채소류 목록");
                                break;
                            case R.id.milk:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(4);
                                //selectedText.setText("우유 및 유제품 목록");
                                break;
                            case R.id.processed:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(5);
                                //selectedText.setText("가공식품 목록");
                                break;
                            case R.id.etc:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(6);
                                //selectedText.setText("기타 재료 목록");
                                break;
                        }
                        //TODO: 새로 정의된 재료리스트 기준으로 뷰 생성을 위한 액티비티 재시작
                        recreate();
                        return false;
                    }
                });
                categoryMenu.show();
            }
        });

        // 저장위치별 버튼
        Button placeButton = findViewById(R.id.placeButton);
        placeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu placeMenu = new PopupMenu(getApplicationContext(), v);

                getMenuInflater().inflate(R.menu.popupmenu_place, placeMenu.getMenu());
                placeMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        TextView selectedText = findViewById(R.id.selectedText);

                        // 각 보관장소 선택 시 화면에 반영
                        switch (item.getItemId()) {
                            case R.id.fridge:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByLocation(((char)97));
                                //selectedText.setText("냉장실 재료 조회");
                                break;
                            case R.id.freezer:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByLocation(((char)98));
                                //selectedText.setText("냉동실 재료 조회");
                                break;
                            case R.id.inside:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByLocation(((char)99));
                                //selectedText.setText("실온 재료 조회");
                                break;
                        }
                        recreate();
                        return false;
                    }
                });
                placeMenu.show();
            }
        });

        // 재료 등록 버튼
        FloatingActionButton addIngredientButton = findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:재료 정보를 가져오기 위한 액티비티 연결
                Intent intent = new Intent(ViewIngredientActivity.this, AddIngredientActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // 재료 스와이프 삭제
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                adapter.removeItem(position);
                adapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    /*
    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            ((MainActivity)MainActivity.mContext).saveIngredient();
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            ((MainActivity)MainActivity.mContext).makeIngredientList();
        }
    });

    Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    });

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            //recreate();
        }
    };


     */


}
