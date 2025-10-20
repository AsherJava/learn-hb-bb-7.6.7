/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 */
package com.jiuqi.gcreport.lease.leasebill.service;

import com.jiuqi.common.base.http.PageInfo;
import java.util.List;
import java.util.Map;

public interface LessorBillService {
    public PageInfo<Map<String, Object>> listLessorBills(Map<String, Object> var1);

    public void batchDelete(List<String> var1);
}

