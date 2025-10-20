/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Map;

public interface SameCtrlBeginOffSetItemService {
    public Map<String, Double> calcBeginSubjectCode2Offset(String var1, String var2, GcOrgCacheVO var3);

    public Map<String, Double> calcBeginSubjectCode2OffsetLimitYear(String var1, String var2, GcOrgCacheVO var3);
}

