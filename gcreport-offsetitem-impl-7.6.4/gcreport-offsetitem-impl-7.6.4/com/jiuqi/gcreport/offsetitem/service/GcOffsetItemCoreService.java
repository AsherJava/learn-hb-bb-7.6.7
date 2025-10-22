/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import java.util.List;

public interface GcOffsetItemCoreService {
    public void save(GcOffSetVchrDTO var1);

    public List<String> getAgingBySubjectCode(String var1, String var2);
}

