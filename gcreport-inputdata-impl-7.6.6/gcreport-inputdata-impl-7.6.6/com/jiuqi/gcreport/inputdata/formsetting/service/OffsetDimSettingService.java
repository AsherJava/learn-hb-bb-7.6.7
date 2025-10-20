/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.inputdata.formsetting.OffsetDimSettingVO
 */
package com.jiuqi.gcreport.inputdata.formsetting.service;

import com.jiuqi.gcreport.inputdata.formsetting.OffsetDimSettingVO;
import java.util.Set;

public interface OffsetDimSettingService {
    public OffsetDimSettingVO getOffsetDimSettingByFormId(String var1);

    public Set<String> getDimsByFormId(String var1, String var2);

    public OffsetDimSettingVO save(OffsetDimSettingVO var1);
}

