/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcOnekeyMergeResultVO
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeResultEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcOnekeyMergeResultVO;
import java.util.List;

public interface GcOnekeyMergeResultService {
    public void saveResult(GcOnekeyMergeResultEO var1);

    public List<GcOnekeyMergeResultVO> getCurrentThreeResult(GcActionParamsVO var1);

    public GcOnekeyMergeResultVO getCurrentResultById(String var1);
}

