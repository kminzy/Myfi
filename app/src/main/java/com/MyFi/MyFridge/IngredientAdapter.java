package com.MyFi.MyFridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    ArrayList<Ingredient> items = new ArrayList<>();

    @NonNull
    @Override
    // 뷰홀더 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ingredient_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    // 뷰홀더 데이터 설정
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Ingredient item) {
        items.add(item);
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName, dDay, expirationDate;

        public ViewHolder(View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredientName);
            dDay = itemView.findViewById(R.id.dDay);
            expirationDate = itemView.findViewById(R.id.expirationDate);
            // 이미지뷰 추가
        }

        public void setItem(Ingredient item) {
            ingredientName.setText(item.getIngredientName());
            dDay.setText(item.getDDay());
            expirationDate.setText(item.getExpirationDate());
        }
    }

}
