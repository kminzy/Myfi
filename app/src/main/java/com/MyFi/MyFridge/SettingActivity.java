package com.MyFi.MyFridge;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Set;

public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
    }

    public static class SettingFragment extends PreferenceFragment {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preference);

            // 알림 주기 설정에서 날짜 선택 시 Summary에 적용
            // TODO: 설정값 유지, 현재 설정화면 다시 실행하면 초기화
            androidx.preference.ListPreference pushAlarmDay = (androidx.preference.ListPreference) findPreference("pushAlarmDay");
            pushAlarmDay.setSummary("알림 받을 날짜를 선택하세요.");
            pushAlarmDay.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // 알림을 받는 날이 당일일 시
                    if(newValue.equals("0")) {
                        preference.setSummary("당일에 알림을 받습니다.");
                    }   // 알림을 받는 날이 당일이 아닐 시
                    else {
                        preference.setSummary(newValue + "일 전에 알림을 받습니다.");
                    }
                    return true;
                }
            });

            final androidx.preference.MultiSelectListPreference cookingTools = (androidx.preference.MultiSelectListPreference) findPreference("cookingTools");
            cookingTools.setSummary("가지고 있는 조리 도구를 선택하세요.");

            cookingTools.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cookingTools.getContext());
                    Set<String> selectedTools = sharedPreferences.getStringSet("cookingTools", null);
                    // TODO: 수정 필요
                    //preference.setSummary(charAt(newValue));
                    return true;
                }
            });

        }
    }

}
