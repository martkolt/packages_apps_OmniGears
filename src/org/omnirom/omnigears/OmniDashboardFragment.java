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
package org.omnirom.omnigears;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.core.PreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OmniDashboardFragment extends DashboardFragment {

    private static final String TAG = "OmniDashboardFragment";
    public static final int ACTION_SETTINGS_OMNI = 948;
    public static final String CATEGORY_OMNI = "com.android.settings.category.ia.omni";

    @Override
    public int getMetricsCategory() {
        return ACTION_SETTINGS_OMNI;
        //return MetricsProto.MetricsEvent.ACTION_SETTINGS_OMNI;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.omni_dashboard_fragment;
    }

    @Override
    protected List<PreferenceController> getPreferenceControllers(Context context) {
        return null;
    }
}
