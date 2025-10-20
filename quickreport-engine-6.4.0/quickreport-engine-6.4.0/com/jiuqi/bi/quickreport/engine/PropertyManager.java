/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.quickreport.engine.IPropertyProvider;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.model.QuickReport;
import java.util.ArrayList;
import java.util.List;

public class PropertyManager {
    public static final String PROP_ETL_SERVER = "com.jiuqi.bi.quickreport.etl.server";
    public static final String PROP_ETL_USERNAME = "com.jiuqi.bi.quickreport.etl.username";
    public static final String PROP_ETL_PASSWORD = "com.jiuqi.bi.quickreport.etl.password";
    private static final List<IPropertyProvider> providers = new ArrayList<IPropertyProvider>();

    private PropertyManager() {
    }

    public static void registerProvider(IPropertyProvider provider) {
        providers.add(0, provider);
    }

    public static void unregisterProvider(IPropertyProvider provider) {
        providers.remove(provider);
    }

    public static Object getProperty(QuickReport report, String propName) throws ReportEngineException {
        for (IPropertyProvider provider : providers) {
            Object value = provider.getProperty(report, propName);
            if (value == null) continue;
            return value;
        }
        return null;
    }
}

