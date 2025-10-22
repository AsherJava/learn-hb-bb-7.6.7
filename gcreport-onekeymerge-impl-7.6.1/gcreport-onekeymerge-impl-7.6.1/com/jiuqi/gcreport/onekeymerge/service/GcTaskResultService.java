/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import java.util.Map;

public interface GcTaskResultService {
    public GcTaskResultEO saveTaskResult(GcTaskResultEO var1);

    public String getResultByTaskCodeAndGroupId(String var1, String var2);

    public Map<String, GcTaskResultVO> getResultByGroupId(String var1);
}

