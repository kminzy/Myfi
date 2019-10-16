package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.MyFi.MyFridge.domain.entitiy.User;
import com.MyFi.MyFridge.httpConnect.HttpConnection;


public class ViewRecipeListActivity extends AppCompatActivity {

    private HttpConnection httpConn = HttpConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecipeListAdapter adapter = new RecipeListAdapter();

        // dummy data
        adapter.addItem(new RecipeList("김치찌개"));
        adapter.addItem(new RecipeList("샌드위치"));
        adapter.addItem(new RecipeList("갈비찜"));

        recyclerView.setAdapter(adapter);

    }





}
