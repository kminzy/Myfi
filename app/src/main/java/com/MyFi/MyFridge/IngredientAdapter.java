package com.MyFi.MyFridge;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        ((MainActivity)MainActivity.mContext).ingredient.setUid(((MainActivity)MainActivity.mContext).user.getUid());
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
            // 재료의 디데이 계산
            long dday = 0;
            /*
            try {
                Calendar todayCal = Calendar.getInstance();
                Calendar expdateCal = Calendar.getInstance();
                String expdate = item.getExp_date();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                expdateCal.setTime(simpleDateFormat.parse(expdate));

                long startDate = todayCal.getTimeInMillis() / (24 * 60 * 60 * 1000);
                long endDate = expdateCal.getTimeInMillis() / (24 * 60 * 60 * 1000);
                dday = endDate - startDate;
            }

            catch(ParseException e) {
                e.printStackTrace();
            }
             */
            dday = item.getDday();
            ingredientName.setText(item.getName());
            dDay.setText(String.valueOf(dday));
            expirationDate.setText(item.getExp_date().toString());
        }
    }
}
