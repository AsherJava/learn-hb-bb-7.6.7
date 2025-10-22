/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.service;

import com.jiuqi.gcreport.intermediatelibrary.entity.ILHandleZbEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.MdZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ZbDataEntity;
import java.util.List;
import java.util.Map;

public interface IntermediateLibraryBulkService {
    public void programmePush(ILHandleZbEntity var1, Map<String, List<ZbDataEntity>> var2, Map<String, List<MdZbDataEntity>> var3);
}

