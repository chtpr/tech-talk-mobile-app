package edu.neu.cs5520.numad22su_group19_techtalk.ui.settings;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_AUTHOR;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_PREFERENCES_KEYS;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_PREF_AUTHOR;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_PREF_NOTIFICATION;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_PREF_THEME;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_PREF_VERSION;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_SETTINGS;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_VERSION;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import edu.neu.cs5520.numad22su_group19_techtalk.R;


public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = "SettingsFragment";
    private boolean enableDarkTheme;
    private boolean enableNotification;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.app_settings, rootKey);

        sharedPreferences = this.getActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

            }
        });
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.SettingStyle, true );
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set preferences
        findPreference(APP_PREF_AUTHOR).setSummary(APP_AUTHOR);
        findPreference(APP_PREF_VERSION).setSummary(APP_VERSION);

        Map<String, ?> preferences = sharedPreferences.getAll();

        for (String key : preferences.keySet()) {
            Preference preference = findPreference(key);
            if (preference != null) {
                if (preference instanceof SwitchPreferenceCompat) {
                    SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) preference;
                    switchPreferenceCompat.setChecked(sharedPreferences.getBoolean(key, false));
                }
                preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        String key = preference.getKey();
                        if (preference instanceof SwitchPreferenceCompat) {
                            sharedPreferences.edit().putBoolean(key, (boolean) newValue).apply();
                        }
                        return true;
                    }
                });
            }

        }
    }

    public static void setInitSettings(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (String key: APP_PREFERENCES_KEYS) {
            if (!sharedPreferences.contains(key)) {
                switch(key) {
                    case APP_PREF_THEME:
                    case APP_PREF_NOTIFICATION:
                        editor.putBoolean(key, true);
                        break;
                    default:
                        editor.putString(key, "");
                }
            }
        }
        editor.apply();
    }
}