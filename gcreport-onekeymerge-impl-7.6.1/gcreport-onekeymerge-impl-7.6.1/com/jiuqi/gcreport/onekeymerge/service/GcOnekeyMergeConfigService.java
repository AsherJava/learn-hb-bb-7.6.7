/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO;
import java.util.List;

public interface GcOnekeyMergeConfigService {
    public void saveConfig(GcConfigVO var1);

    public void deleteConfig(String var1);

    public void updateConfig(GcConfigVO var1);

    public List<GcConfigVO> getConfigByUser();
}

