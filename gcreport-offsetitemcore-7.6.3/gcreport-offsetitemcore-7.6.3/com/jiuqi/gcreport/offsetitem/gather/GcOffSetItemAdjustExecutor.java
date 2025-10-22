/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather;

import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import java.util.List;
import java.util.Map;

public interface GcOffSetItemAdjustExecutor {
    public GcOffSetItemAction getActionForCode(GcOffsetExecutorVO var1);

    public List<Map<String, String>> listShowTypeForPage(String var1);

    public List<GcOffsetItemShowType> listShowTypeForDataSource(String var1);

    public List<GcOffsetItemShowType> listShowTypesForCondition(String var1, String var2);
}

