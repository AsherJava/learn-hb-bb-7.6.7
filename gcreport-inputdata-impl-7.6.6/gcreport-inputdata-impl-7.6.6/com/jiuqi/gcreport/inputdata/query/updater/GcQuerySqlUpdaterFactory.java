/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.query.updater;

import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlFloatUpdater;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlUpdater;

public class GcQuerySqlUpdaterFactory {
    public static final GcQuerySqlUpdater createSqlUpdater(GcDataEntryContext gcContext) {
        if (gcContext.isGcQuery()) {
            return new GcQuerySqlFloatUpdater(gcContext);
        }
        return null;
    }
}

