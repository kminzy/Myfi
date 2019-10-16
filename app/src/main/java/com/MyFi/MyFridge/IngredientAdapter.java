package com.MyFi.MyFridge;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    //TODO : 메인에 생성된 유저 재료리스트 기준으로 리스트뷰 아이템 리스트 구성
    List<IngredientData> items = ((MainActivity)MainActivity.mContext).callIngredientList();


    @NonNull
    @Override
    // 뷰홀더 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ingredient_item, parent, false);
        Log.d(this.getClass().getName(),"뷰홀더 생성");

        return new ViewHolder(itemView);
    }

    @Override
    // 뷰홀더 데이터 설정
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientData item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(IngredientData item) {

        items.add(item);
    }

    public void removeItem(int position) {
        ((MainActivity)MainActivity.mContext).ingredient.setIngredientData(items.get(position));
        ((MainActivity)MainActivity.mContext).ingredient.setUid(1);
        //TODO: 삭제할 재료 데이터 서버에 전달
        ((MainActivity)MainActivity.mContext).deleteIngredient();
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

        public void setItem(IngredientData item) {
            ingredientName.setText(item.getName());
            dDay.setText("7");
            expirationDate.setText(item.getExp_date().toString());
        }
    }

    public void setList() {
        //TODO: 메인액티비티에서 불러온 유저 데이터 리스트로 리스트뷰 대상 구성
        ((MainActivity)MainActivity.mContext).user.setUid(1);
        ((MainActivity)MainActivity.mContext).makeIngredientList();
        final MainActivity mainActivity = new MainActivity();
        this.items = mainActivity.myIngredientList;
    }

}
