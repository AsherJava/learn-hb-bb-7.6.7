/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.service;

import com.jiuqi.nr.summary.vo.SummaryConfigVO;

public interface ISummaryConfigService {
    public void saveConfig(SummaryConfigVO var1);

    public SummaryConfigVO getConfig(String var1);
}

