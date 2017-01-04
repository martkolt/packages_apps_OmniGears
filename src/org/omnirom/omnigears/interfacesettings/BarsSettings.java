/*
 *  Copyright (C) 2017 The OmniROM Project
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

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.net.TrafficStats;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceCategory;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;
import com.android.settings.dashboard.SummaryLoader;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import com.android.internal.util.omni.DeviceUtils;
import com.android.settings.Utils;

import java.util.List;
import java.util.ArrayList;

public class BarsSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, Indexable {
    private static final String TAG = "BarsSettings";
    private static final String NETWORK_TRAFFIC_ROOT = "category_network_traffic";
    private static final String NAVIGATIONBAR_ROOT = "category_navigationbar";

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.OMNI_SETTINGS;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.bars_settings);

        PreferenceScreen prefScreen = getPreferenceScreen();

        // Navigationbar catagory will not be displayed when the device is not a tablet
        // or the device has physical keys
        if ((!DeviceUtils.deviceSupportNavigationBar(getActivity())) || DeviceUtils.isPhone(getActivity())) {
            prefScreen.removePreference(findPreference(NAVIGATIONBAR_ROOT));
        }

        // TrafficStats will return UNSUPPORTED if the device does not support it.
        if (TrafficStats.getTotalTxBytes() == TrafficStats.UNSUPPORTED ||
                TrafficStats.getTotalRxBytes() == TrafficStats.UNSUPPORTED) {
            prefScreen.removePreference(findPreference(NETWORK_TRAFFIC_ROOT));
        }

        //final boolean customHeaderImage = Settings.System.getInt(getContentResolver(),
        //        Settings.System.STATUS_BAR_CUSTOM_HEADER, 0) == 1;
        //mCustomHeaderImage = (CheckBoxPreference) findPreference(CUSTOM_HEADER_IMAGE);
        //mCustomHeaderImage.setChecked(customHeaderImage);

        //String settingHeaderPackage = Settings.System.getString(getContentResolver(),
        //        Settings.System.STATUS_BAR_DAYLIGHT_HEADER_PACK);
        //if (settingHeaderPackage == null) {
        //    settingHeaderPackage = DEFAULT_HEADER_PACKAGE;
        //}
        //mDaylightHeaderPack = (ListPreference) findPreference(DAYLIGHT_HEADER_PACK);

        //List<String> entries = new ArrayList<String>();
        //List<String> values = new ArrayList<String>();
        //getAvailableHeaderPacks(entries, values);
        //mDaylightHeaderPack.setEntries(entries.toArray(new String[entries.size()]));
        //mDaylightHeaderPack.setEntryValues(values.toArray(new String[values.size()]));

        //int valueIndex = mDaylightHeaderPack.findIndexOfValue(settingHeaderPackage);
        //if (valueIndex == -1) {
            // no longer found
        //    settingHeaderPackage = DEFAULT_HEADER_PACKAGE;
        //    Settings.System.putString(getContentResolver(),
        //            Settings.System.STATUS_BAR_DAYLIGHT_HEADER_PACK, settingHeaderPackage);
        //    valueIndex = mDaylightHeaderPack.findIndexOfValue(settingHeaderPackage);
        //}
        //mDaylightHeaderPack.setValueIndex(valueIndex >= 0 ? valueIndex : 0);
        //mDaylightHeaderPack.setSummary(mDaylightHeaderPack.getEntry());
        //mDaylightHeaderPack.setOnPreferenceChangeListener(this);

        //mHeaderShadow = (SeekBarPreference) findPreference(CUSTOM_HEADER_IMAGE_SHADOW);
        //final int headerShadow = Settings.System.getInt(getContentResolver(),
        //        Settings.System.STATUS_BAR_CUSTOM_HEADER_SHADOW, 0);
        //mHeaderShadow.setValue((int)(((double) headerShadow / 255) * 100));
        //mHeaderShadow.setOnPreferenceChangeListener(this);

        //mQuickPulldown = (ListPreference) findPreference(QUICK_SETTTINGS_PULLDOWN);
        //mQuickPulldown.setOnPreferenceChangeListener(this);
        //int quickPullDownValue = Settings.System.getInt(getContentResolver(), Settings.System.STATUS_BAR_QUICK_QS_PULLDOWN, 0);
        //mQuickPulldown.setValue(String.valueOf(quickPullDownValue));
        //updatePulldownSummary(quickPullDownValue);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        //if (preference == mCustomHeaderImage) {
        //    final boolean value = ((CheckBoxPreference)preference).isChecked();
        //    Settings.System.putInt(getContentResolver(),
        //            Settings.System.STATUS_BAR_CUSTOM_HEADER, value ? 1 : 0);
        //    return true;
        //}
        // If we didn't handle it, let preferences handle it.
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                        boolean enabled) {
                    ArrayList<SearchIndexableResource> result =
                            new ArrayList<SearchIndexableResource>();

                    SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.bars_settings;
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
