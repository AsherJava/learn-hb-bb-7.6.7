/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.offsetitem.check;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.offsetitem.check.IOffsetGroupChecker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OffsetItemCheckGather {
    private static List<IOffsetGroupChecker> offsetGroupChecks;

    public static Collection<IOffsetGroupChecker> getGroupValidatorList() {
        if (offsetGroupChecks == null) {
            Collection beans = SpringContextUtils.getBeans(IOffsetGroupChecker.class);
            offsetGroupChecks = beans == null ? Collections.emptyList() : new ArrayList(beans);
        }
        return offsetGroupChecks;
    }
}

