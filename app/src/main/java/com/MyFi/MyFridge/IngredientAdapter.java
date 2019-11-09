package com.MyFi.MyFridge;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        ImageView location;
        public ViewHolder(View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredientName);
            dDay = itemView.findViewById(R.id.dDay);
            expirationDate = itemView.findViewById(R.id.expirationDate);
            location = itemView.findViewById(R.id.placeImageView);
            // 이미지뷰 추가
        }

        public void setItem(IngredientData item) {
            // 재료의 디데이 계산
            long dday = 0;

            dday = item.getDday();

            if(dday<=0)
            {
                dDay.setText("유통기한 만료");
            }
            else if(dday>300000)
            {
                dDay.setText("");
            }
            else
            {
                dDay.setText("D - " + dday);
            }

            switch (item.getLocation()){
                case (char)97 : location.setImageResource(R.drawable.settings); break;
                case (char)98 : location.setImageResource(R.drawable.snowflake); break;
                case (char)99 : location.setImageResource(R.drawable.ic_launcher_foreground); break;
            }


            ingredientName.setText(item.getName());
            expirationDate.setText("등록일 : " +item.getAdd_date());
        }
    }
}
