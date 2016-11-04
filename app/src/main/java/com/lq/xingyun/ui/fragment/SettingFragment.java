package com.lq.xingyun.ui.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.lq.xingyun.R;
import com.lq.xingyun.inter.ISettingChangeListener;
import com.lq.xingyun.utils.CommonUtils;
import com.lq.xingyun.utils.SharedPreferencesUtils;

/**
 * Created by lenovo on 2016/8/31.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private ListPreference languagePreference;
    private Preference nightModePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        languagePreference = (ListPreference) findPreference("language");
        nightModePreference = findPreference("night_mode");
        languagePreference.setOnPreferenceChangeListener(this);
        nightModePreference.setOnPreferenceChangeListener(this);

        languagePreference.setSummary(getActivity().getResources().getStringArray(R.array.languages)[CommonUtils.getCurrentLanguage()]);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == nightModePreference) {
            boolean isNight = Boolean.valueOf(newValue.toString());
            SharedPreferencesUtils.save( "night_mode", isNight);
            ((ISettingChangeListener) getActivity()).onChangeTheme();
            return true;
        } else if (preference == languagePreference) {
//            languagePreference.setSummary(String.valueOf(newValue));
            int index=languagePreference.findIndexOfValue(String.valueOf(newValue));
            SharedPreferencesUtils.save("language_mode",index);
            ((ISettingChangeListener) getActivity()).onChangeLanguage();
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        return false;
    }
}
