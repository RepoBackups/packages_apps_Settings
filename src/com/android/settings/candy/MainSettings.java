/*
 * Copyright (C) 2014 The Candy5 Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.candy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.SystemProperties;
import com.android.settings.search.Indexable;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.CheckBoxPreference;
import android.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import android.app.Activity;
import java.util.List;
import android.util.Log;
import android.preference.PreferenceManager;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;



 

public class MainSettings extends SettingsPreferenceFragment  implements
        Preference.OnPreferenceChangeListener, Indexable {
private static final String TAG = "MainSettings";

private static final String KEY_VOLBTN_MUSIC_CTRL = "volbtn_music_controls";
private static final String KEY_NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
private SwitchPreference mVolBtnMusicCtrl;
private ListPreference mNavigationBarHeight;

         @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         final Activity activity = getActivity();
         final ContentResolver resolver = activity.getContentResolver();

        addPreferencesFromResource(R.xml.candy_main_settings);
          
        PreferenceScreen prefSet = getPreferenceScreen();

        mNavigationBarHeight = (ListPreference) findPreference(KEY_NAVIGATION_BAR_HEIGHT);
        mNavigationBarHeight.setOnPreferenceChangeListener(this);
       
        mVolBtnMusicCtrl = (SwitchPreference) findPreference(KEY_VOLBTN_MUSIC_CTRL);
        mVolBtnMusicCtrl.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.VOLUME_MUSIC_CONTROLS, 1) != 0);
        mVolBtnMusicCtrl.setOnPreferenceChangeListener(this);

        int statusNavigationBarHeight = Settings.System.getInt(getActivity().getApplicationContext()
                .getContentResolver(),
                Settings.System.NAVIGATION_BAR_HEIGHT, 48);
        mNavigationBarHeight.setValue(String.valueOf(statusNavigationBarHeight));
        mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntry());
        }
     
         @Override
     public boolean onPreferenceChange(Preference preference, Object objValue) {
       final String key = preference.getKey();
         if (preference == mNavigationBarHeight) {
            int statusNavigationBarHeight = Integer.valueOf((String) objValue);
            int index = mNavigationBarHeight.findIndexOfValue((String) objValue);
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.NAVIGATION_BAR_HEIGHT, statusNavigationBarHeight);
            mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntries()[index]);
      
        if (KEY_VOLBTN_MUSIC_CTRL.equals(key)) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.VOLUME_MUSIC_CONTROLS,
                    (Boolean) objValue ? 1 : 0);
            }
          }         
        return true;
      }
  }
