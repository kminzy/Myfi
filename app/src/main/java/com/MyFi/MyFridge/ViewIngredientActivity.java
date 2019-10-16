package com.MyFi.MyFridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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

        /*
        List<String> names = new ArrayList<String>();
        names.add(mainActivity.myIngredientList.get(0).getName());
        names.add(mainActivity.myIngredientList.get(1).getName());
        names.add(mainActivity.myIngredientList.get(2).getName());

        // dummy data
        adapter.addItem(new Ingredient(names.get(0), "D-10", "~2019.10.08"));
        adapter.addItem(new Ingredient(names.get(1), "D-5", "~2019.10.03"));
        adapter.addItem(new Ingredient(names.get(2), "D-7", "~2019.10.04"));
        */

        // 전체 버튼
        Button allButton = findViewById(R.id.allButton);
        allButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전체 선택 시 화면의 텍스트뷰에 반영
                TextView selectedText = findViewById(R.id.selectedText);
                selectedText.setText("전체 재료 목록");
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
                                selectedText.setText("육류 목록");
                                break;
                            case R.id.fish:
                                ((MainActivity)MainActivity.mContext).makeIngredientListByType(6);
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
                //TODO:재료 정보를 가져오기 위한 액티비티 연결
                Intent intent = new Intent(ViewIngredientActivity.this, AddIngredientActivity.class);
                startActivityForResult(intent,1);
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

    //TODO: 다음 액티비티로 부터 받아온 재료 데이터를 어댑터의 재료 리스트에 추가
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
// MainActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 1:
                    IngredientData result = data.getParcelableExtra("result");
                    adapter.addItem(result);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
