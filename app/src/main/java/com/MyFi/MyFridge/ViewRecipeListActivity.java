package com.MyFi.MyFridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.MyFi.MyFridge.domain.entitiy.User;
import com.MyFi.MyFridge.httpConnect.HttpConnection;


public class ViewRecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecipeListAdapter adapter = new RecipeListAdapter();

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }





}
