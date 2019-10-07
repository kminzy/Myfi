package com.MyFi.MyFridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewIngredientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ingredient);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final IngredientAdapter adapter = new IngredientAdapter();
        recyclerView.setAdapter(adapter);

        // dummy data
        adapter.addItem(new Ingredient("김치", "D-10", "~2019.10.08"));
        adapter.addItem(new Ingredient("돼지고기", "D-5", "~2019.10.03"));
        adapter.addItem(new Ingredient("식빵", "D-7", "~2019.10.04"));

        // 전체 버튼
        Button allButton = findViewById(R.id.allButton);
        allButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전체 선택 시 화면의 텍스트뷰에 반영
                TextView selectedText = findViewById(R.id.selectedText);
                selectedText.setText("전체 재료 목록");
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
                                selectedText.setText("육류 목록");
                                break;
                            case R.id.fish:
                                selectedText.setText("어패류 목록");
                                break;
                            case R.id.fruitVegetable:
                                selectedText.setText("과일 및 채소류 목록");
                                break;
                            case R.id.milk:
                                selectedText.setText("우유 및 유제품 목록");
                                break;
                            case R.id.processed:
                                selectedText.setText("가공식품 목록");
                                break;
                            case R.id.etc:
                                selectedText.setText("기타 재료 목록");
                                break;
                        }
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
                                selectedText.setText("냉장실 재료 조회");
                                break;
                            case R.id.freezer:
                                selectedText.setText("냉동실 재료 조회");
                                break;
                            case R.id.inside:
                                selectedText.setText("실온 재료 조회");
                                break;
                        }
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
                Intent intent = new Intent(ViewIngredientActivity.this, AddIngredientActivity.class);
                startActivity(intent);
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
}
