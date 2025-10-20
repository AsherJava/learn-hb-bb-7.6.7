/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import java.util.Collection;

public class GcFormulaThreadContext {
    public static void enableCache() {
        Collection iFunctionCaches = SpringContextUtils.getBeans(IFunctionCache.class);
        for (IFunctionCache iFunctionCach : iFunctionCaches) {
            iFunctionCach.enableCache();
        }
    }

    public static void releaseCache() {
        Collection iFunctionCaches = SpringContextUtils.getBeans(IFunctionCache.class);
        for (IFunctionCache iFunctionCache : iFunctionCaches) {
            iFunctionCache.releaseCache();
        }
    }
}

