package com.MyFi.MyFridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MyFi.MyFridge.domain.entitiy.IngredientData;

import java.util.ArrayList;
import java.util.List;

public class InitialSettingActivity extends AppCompatActivity {
    IngredientData ingredient = new IngredientData();
    public static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setting);
        context = this;

        final CheckBox rangeCheck = findViewById(R.id.rangeCheck);
        final CheckBox airfryerCheck = findViewById(R.id.airfryerCheck);
        final CheckBox ovenCheck = findViewById(R.id.ovenCheck);

        final CheckBox redPepperPowderCheck = findViewById(R.id.redPepperPowderCheck);
        final CheckBox redPepperPasteCheck = findViewById(R.id.redPepperPasteCheck);
        final CheckBox soybeanPasteCheck = findViewById(R.id.soybeanPasteCheck);
        final CheckBox soySauceCheck = findViewById(R.id.soySauceCheck);
        final CheckBox sesameOilCheck = findViewById(R.id.sesameOilCheck);
        final CheckBox perillaOilCheck = findViewById(R.id.perillaOilCheck);
        final CheckBox honeyCheck = findViewById(R.id.honeyCheck);
        final CheckBox cornSyrupCheck = findViewById(R.id.cornSyrupCheck);
        final CheckBox oligodangCheck = findViewById(R.id.oligodangCheck);
        final CheckBox sugarCheck = findViewById(R.id.sugarCheck);
        final CheckBox saltCheck = findViewById(R.id.saltCheck);
        final CheckBox pepperCheck = findViewById(R.id.pepperCheck);

        Button initialButton = findViewById(R.id.initialButton);
        initialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rangeCheck.isChecked()) { }
                if(airfryerCheck.isChecked()){ }
                if(ovenCheck.isChecked()) { }

                if(redPepperPowderCheck.isChecked()) { }
                if(redPepperPasteCheck.isChecked()){}
                if(soybeanPasteCheck.isChecked()) {}
                if(soySauceCheck.isChecked()) {}
                if(sesameOilCheck.isChecked()) {}
                if(perillaOilCheck.isChecked()) {}
                if(honeyCheck.isChecked()) {}
                if(cornSyrupCheck.isChecked()) {}
                if(oligodangCheck.isChecked()) {}
                if(sugarCheck.isChecked()) {}
                if(saltCheck.isChecked()) {}
                if(pepperCheck.isChecked()) {}

                Intent intent = new Intent(InitialSettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
