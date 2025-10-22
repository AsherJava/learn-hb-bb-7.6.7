/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 */
package com.jiuqi.gcreport.billcore.offsetcheck.offsetitemcheck;

import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import java.util.List;
import java.util.Map;

public interface OffsetItemCheck {
    public String getType();

    public String getTitle();

    public OffsetCheckResultDTO check(List<Map<String, Object>> var1, List<GcOffSetVchrItemDTO> var2, String var3);
}

