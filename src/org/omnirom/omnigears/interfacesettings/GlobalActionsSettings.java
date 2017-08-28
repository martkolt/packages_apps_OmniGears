/*
 *  Copyright (C) 2016 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
*/
package org.omnirom.omnigears.interfacesettings;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.omnirom.omnigears.preference.SecureSettingSwitchPreference;
import org.omnirom.omnigears.OmniDashboardFragment;

public class GlobalActionsSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, Indexable {
    private static final String TAG = "GlobalActionsSettings";
    private static final String POWER_MENU_ANIMATIONS = "power_menu_animations";

    private ListPreference mPowerMenuAnimations;
    private SecureSettingSwitchPreference mAdvancedReboot;

    @Override
    public int getMetricsCategory() {
        return OmniDashboardFragment.ACTION_SETTINGS_OMNI;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.global_actions);

        final PreferenceScreen prefScreen = getPreferenceScreen();
        final ContentResolver contentResolver = getContext().getContentResolver();

        mPowerMenuAnimations = (ListPreference) findPreference(POWER_MENU_ANIMATIONS);
        mPowerMenuAnimations.setValue(String.valueOf(Settings.System.getInt(
                getContentResolver(), Settings.System.POWER_MENU_ANIMATIONS, 0)));
        mPowerMenuAnimations.setSummary(mPowerMenuAnimations.getEntry());
        mPowerMenuAnimations.setOnPreferenceChangeListener(this);

        mAdvancedReboot = (SecureSettingSwitchPreference)
                findPreference(Settings.Secure.ADVANCED_REBOOT);
        mAdvancedReboot.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
         boolean result = false;
         if (preference instanceof ListPreference) {
             if (preference == mPowerMenuAnimations) {
                Settings.System.putInt(getContentResolver(), Settings.System.POWER_MENU_ANIMATIONS,
                        Integer.valueOf((String) objValue));
                mPowerMenuAnimations.setValue(String.valueOf(objValue));
                mPowerMenuAnimations.setSummary(mPowerMenuAnimations.getEntry());
                return true;
             }
         } else if (preference instanceof SecureSettingSwitchPreference) {
             if (preference == mAdvancedReboot) {
                boolean value = (Boolean) objValue;
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.ADVANCED_REBOOT,
                        value ? 1:0);
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.GLOBAL_ACTION_DNAA,
                        value ? 1:0);
             }
             return true;
         }
         return result;
    }

    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                        boolean enabled) {
                    ArrayList<SearchIndexableResource> result =
                            new ArrayList<SearchIndexableResource>();

                    SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.global_actions;
                    result.add(sir);

                    return result;
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    ArrayList<String> result = new ArrayList<String>();
                    return result;
                }
            };
}
