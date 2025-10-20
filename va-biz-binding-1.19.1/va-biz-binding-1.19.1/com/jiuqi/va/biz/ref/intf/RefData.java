/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ref.intf;

import com.jiuqi.va.biz.ref.intf.RefDataFilter;
import com.jiuqi.va.biz.ref.intf.RefDataManager;
import com.jiuqi.va.biz.ref.intf.RefDataObject;

public class RefData {
    public static void setInstance(RefDataManager instance) {
        RefDataManagerHolder.instance = instance;
    }

    public static boolean isEnabled(int refTableType) {
        RefDataManager instance = RefDataManagerHolder.instance;
        if (instance == null) {
            return false;
        }
        return refTableType != 1 || instance.isUseBasedataClient();
    }

    public static RefDataObject ref(int refDataType, RefDataFilter filter) {
        return RefDataManagerHolder.instance.findProvider(refDataType).ref(filter);
    }

    public static void flushAll() {
        if (RefData.isEnabled(-1)) {
            RefDataManagerHolder.instance.flushAll();
        }
    }

    private static class RefDataManagerHolder {
        private static RefDataManager instance;

        private RefDataManagerHolder() {
        }
    }
}

