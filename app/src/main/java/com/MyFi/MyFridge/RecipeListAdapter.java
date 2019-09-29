package com.MyFi.MyFridge;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private ArrayList<RecipeList> items = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    // 뷰홀더 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recipe_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    // 뷰홀더 데이터 설정
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeList item = items.get(position);
        holder.setItem(item);
    }

    @Override
    // 데이터 수 반환
    public int getItemCount() {
        return items.size();
    }

    public void addItem(RecipeList item) {
        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;

        public ViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);
            // 이미지뷰 추가

            // 아이템 클릭 리스너
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context = v.getContext();
                    Intent intent = new Intent(context, ViewRecipeActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        // 데이터에 레시피명 설정
        public void setItem(RecipeList item) {
            recipeName.setText(item.getRecipeName());
        }
    }
}
