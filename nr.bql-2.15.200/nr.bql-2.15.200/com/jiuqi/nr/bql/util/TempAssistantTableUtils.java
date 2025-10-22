/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.bql.util;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import java.util.HashMap;

public class TempAssistantTableUtils {
    public static HashMap<String, OrderTempAssistantTable> getContextTempAssistantTables(QueryContext qContext) {
        HashMap tempAssistantTables = (HashMap)qContext.getCache().get("TempAssistantTableCache");
        if (tempAssistantTables == null) {
            tempAssistantTables = new HashMap();
            qContext.getCache().put("TempAssistantTableCache", tempAssistantTables);
        }
        return tempAssistantTables;
    }
}

