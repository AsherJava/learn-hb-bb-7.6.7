/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.np.dataengine.common.LookupKeyItem;
import com.jiuqi.np.dataengine.common.QueryFieldBuilder;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TableAllocator {
    private static final ReentrantLock lockObj = new ReentrantLock();
    private static int tableIndex = 0;
    private static int lookupIndex = 0;
    private static HashMap<QueryFieldBuilder, Integer> tableAlias = new HashMap();
    private static HashMap<LookupKeyItem, Integer> lookupAlias = new HashMap();
    private static final String LOOKUP_PREFIX = "lf_";

    public static int getQueryTableAlias(QueryFieldBuilder queryFieldBuilder) {
        if (tableAlias.containsKey(queryFieldBuilder)) {
            return tableAlias.get(queryFieldBuilder);
        }
        lockObj.lock();
        try {
            int resultValue;
            if (tableAlias.containsKey(queryFieldBuilder)) {
                int n = tableAlias.get(queryFieldBuilder);
                return n;
            }
            tableAlias.put(queryFieldBuilder, tableIndex);
            int n = resultValue = tableIndex++;
            return n;
        }
        finally {
            lockObj.unlock();
        }
    }

    public static String getLookupAlias(LookupKeyItem lookupKeyItem) {
        Integer tableUid = TableAllocator.getLookupAliasUid(lookupKeyItem);
        return LOOKUP_PREFIX.concat(tableUid.toString());
    }

    public static int getLookupAliasUid(LookupKeyItem lookupKeyItem) {
        if (lookupAlias.containsKey(lookupKeyItem)) {
            return lookupAlias.get(lookupKeyItem);
        }
        lockObj.lock();
        try {
            int resultValue;
            if (lookupAlias.containsKey(lookupKeyItem)) {
                int n = lookupAlias.get(lookupKeyItem);
                return n;
            }
            lookupAlias.put(lookupKeyItem, lookupIndex);
            int n = resultValue = lookupIndex++;
            return n;
        }
        finally {
            lockObj.unlock();
        }
    }
}

